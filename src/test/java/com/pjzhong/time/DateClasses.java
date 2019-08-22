package com.pjzhong.time;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import org.junit.Test;

/**
 * @link https://docs.oracle.com/javase/tutorial/datetime/iso/date.html
 * @since 2019年03月21日 15:25:14
 **/
public class DateClasses {

  @Test
  public void LocalDate() {
    // A LocalDate represents a year-month-day in the ISO calendar and
    // is useful for representing a date without a com.pjzhong.time
    LocalDate date = LocalDate.of(1995, Month.MARCH, 21);
    LocalDate nextWed = date.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
    System.out.println(date);
    System.out.println(nextWed);
    System.out.printf("For the date of %s, the next Wednesday is %s.%n",
        date, nextWed);

    DayOfWeek dotw = date.getDayOfWeek();
    System.out.println(dotw);

    // Is Same Day
    LocalDate now = LocalDate.from(LocalDateTime
        .ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault()));
    System.out.printf("%s and %s is same day %s%n", now, date, now.equals(date));
  }

  @Test
  public void YearMonth() {
    // The YearMonth class represents the month of a specific year
    YearMonth curMonth = YearMonth.now();
    System.out.printf("%s: %d%n", curMonth, curMonth.lengthOfMonth());

    YearMonth feb = YearMonth.of(curMonth.getYear(), Month.FEBRUARY);
    System.out.printf("%s: %d%n", feb, feb.lengthOfMonth());

    YearMonth prevFeb = YearMonth.of(curMonth.minusYears(1).getYear(), Month.FEBRUARY);
    System.out.printf("%s: %d%n", prevFeb, prevFeb.lengthOfMonth());
  }

  @Test
  public void MonthDay() {
    // The MonthDay class represents the day of a particular month, such as New Year's
    // Day on January 1

    MonthDay date = MonthDay.of(Month.FEBRUARY, 29);
    System.out.println(date);
    System.out.println(date.getDayOfMonth());
    System.out.println(date.isValidYear(2010));

  }

  @Test
  public void Year() {
    // The Year class represents a year
    Year year = Year.of(2012);
    System.out.println(year);
    System.out.println(year.isLeap());
  }
}
