package com.pjzhong.time;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import org.junit.Test;

/**
 * The java.com.pjzhong.time.temporal package provides a collection of interfaces, classes, and
 * enums that support date and com.pjzhong.time code and, in particular, date and com.pjzhong.time
 * calculations
 *
 * @link https://docs.oracle.com/javase/tutorial/datetime/iso/temporal.html
 * @since 2019年03月22日 13:50:58
 **/
public class TheTemporalPackage {

  @Test
  public void PredefinedAdjusters() {
    LocalDate date = LocalDate.now();
    DayOfWeek dotw = date.getDayOfWeek();
    System.out.printf("%s is on a %s%n", date, dotw);

    System.out.printf("first day of Year:%s%n", date.with(TemporalAdjusters.firstDayOfYear()));
    System.out.printf("first day of Month:%s%n", date.with(TemporalAdjusters.firstDayOfMonth()));
    System.out.printf("first Monday of Month:%s%n",
        date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)));
    System.out.printf("last day of Month:%s%n", date.with(TemporalAdjusters.lastDayOfMonth()));
    System.out.printf("last Monday Of Month:%s%n",
        date.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY)));
    System.out.printf("last day of this year:%s%n", date.with(TemporalAdjusters.lastDayOfYear()));
    System.out
        .printf("first day of next Month:%s%n", date.with(TemporalAdjusters.firstDayOfNextMonth()));
    System.out
        .printf("first day of next Year:%s%n", date.with(TemporalAdjusters.firstDayOfNextYear()));
  }

  /**
   * @link https://docs.oracle.com/javase/tutorial/datetime/iso/adjusters.html#Custom Adjusters
   * @since 2019年03月22日 17:01:44
   */
  @Test
  public void CustomAdjusters() {
    TemporalAdjuster payDayAdjuster = input -> {
      LocalDate date = LocalDate.from(input);
      int day;
      if (date.getDayOfMonth() < 15) {
        day = 15;
      } else {
        day = date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
      }
      date = date.withDayOfMonth(day);
      if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
        date = date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
      }
      return input.with(date);
    };

    LocalDate now = LocalDate.now();
    LocalDate nextPayday = now.with(payDayAdjuster);
    System.out.printf("Given the date:%s%n", now);
    System.out.printf("the next payday:%s%n", nextPayday);

  }

  @Test
  public void PredefinedQueries() {
    TemporalQuery<TemporalUnit> query = TemporalQueries.precision();
    System.out.printf("LocalDate precision is %s%n", LocalDate.now().query(query));
    System.out.printf("LocalDateTime precision is %s%n", LocalDateTime.now().query(query));
    System.out.printf("Year precision is %s%n", Year.now().query(query));
    System.out.printf("YearMonth precision is %s%n", YearMonth.now().query(query));
    System.out.printf("Instant precision is %s%n", Instant.now().query(query));
  }

  @Test
  public void CustomQueries() {
    TemporalQuery<Boolean> familyVacations = date -> {
      int month = date.get(ChronoField.MONTH_OF_YEAR);
      int day = date.get(ChronoField.DAY_OF_MONTH);

      if ((month == Month.APRIL.getValue()) && ((day >= 3) && (day <= 8))) {
        return true;
      }

      if ((month == Month.AUGUST.getValue()) && ((day >= 8) && (day <= 14))) {
        return true;
      }

      return false;
    };

    // birthday
    LocalDateTime date = LocalDateTime.of(1995, Month.MARCH, 21, 0, 0);
    boolean isFamilyVacation = date.query(familyVacations);
    boolean isFamilyBirthday = date.query(this::isFamilyBirthday);

    if (isFamilyBirthday || isFamilyVacation) {
      System.out.printf("%s is an important date!%n", date);
    } else {
      System.out.printf("%s is not an important date.%n", date);
    }
  }

  private boolean isFamilyBirthday(TemporalAccessor date) {
    int month = date.get(ChronoField.MONTH_OF_YEAR);
    int day = date.get(ChronoField.DAY_OF_MONTH);

    //My birthday
    if (month == Month.MARCH.getValue() && day == 21) {
      return true;
    }
    //Who it is?
    if (month == Month.JUNE.getValue() && day == 18) {
      return true;
    }
    //What is your name?
    if (month == Month.MAY.getValue() && day == 29) {
      return true;
    }

    return false;
  }
}
