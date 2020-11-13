package com.pjzhong.time;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import org.junit.Test;

/**
 * The Date-Time API provides four classes that deal exclusively with date information, without
 * respect to com.pjzhong.time or com.pjzhong.time zone
 *
 * @link https://docs.oracle.com/javase/tutorial/datetime/iso/enum.html
 * @since 2019年03月21日 15:25:14
 **/
public class DayOfWeekAndMonthEnums {

  @Test
  public void DayOfWeek() {
    // MONDAY plus 3 days
    System.out.printf("%s%n\n", DayOfWeek.MONDAY.plus(3));

    DayOfWeek dow = DayOfWeek.MONDAY;
    Locale locale = Locale.getDefault();
    System.out.println(dow.getDisplayName(TextStyle.FULL, locale));
    System.out.println(dow.getDisplayName(TextStyle.NARROW, locale));
    System.out.println(dow.getDisplayName(TextStyle.SHORT, locale));
  }

  @Test
  public void Month() {
    // the maximum possible days in FEBRUARY
    System.out.printf("%d%n", Month.FEBRUARY.maxLength());

    Month month = Month.AUGUST;
    Locale locale = Locale.getDefault();
    System.out.println(month.getDisplayName(TextStyle.FULL, locale));
    System.out.println(month.getDisplayName(TextStyle.NARROW, locale));
    System.out.println(month.getDisplayName(TextStyle.SHORT, locale));
  }
}
