package com.pjzhong.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import org.junit.Test;

/**
 * @link https://docs.oracle.com/javase/tutorial/datetime/iso/datetime.html
 * @since 2019年03月21日 15:58:07
 **/
public class DateAndTimeClasses {

  @Test
  public void LocalTime() {
    // The LocalTime class is similar to the other classes whose names are prefixed with Local,
    // but deals in com.pjzhong.time only
    LocalTime now = LocalTime.now();
    System.out.println(now);
  }

  @Test
  public void LocalDateTime() {
    // The class that handles both date and com.pjzhong.time, without a com.pjzhong.time zone, is LocalDateTime
    // , one of the core classes of the Date-Time API. This class is used to represent date
    // (month-day-year) together with com.pjzhong.time (hour-minute-second-nanosecond) and is, in effect,
    // a combination of LocalDate with LocalTime
    System.out.printf("now: %s%n", LocalDateTime.now());
    System.out.printf("now (from Instant):%s%n",
        LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
    System.out.printf("6 months from now: %s%n", LocalDateTime.now().plusMonths(6));
    System.out.printf("6 months ago: %s%n", LocalDateTime.now().minusMonths(6));
    System.out
        .printf("1995-03-2119:03:21 : %s%n", LocalDateTime.of(1995, Month.MARCH, 21, 19, 3, 21));
  }
}
