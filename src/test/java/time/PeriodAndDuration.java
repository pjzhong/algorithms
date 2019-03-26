package time;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import org.junit.Test;

/**
 * @link https://docs.oracle.com/javase/tutorial/datetime/iso/period.html
 * @since 2019年03月26日 19:18:45
 **/
public class PeriodAndDuration {

  /**
   * A Duration is most suitable in situations that measure machine-based time, such as code that
   * uses an Instant object
   *
   * @since 2019年03月26日 19:22:45
   */
  @Test
  public void Duration() {
    Instant t1 = Instant.now(), t2 = Instant.EPOCH;
    Duration duration = Duration.between(t2, t1);
    System.out.println(duration.toNanos());
    System.out.println(duration.toMillis());
  }

  @Test
  public void ChronoUnit() {
    Instant t1 = Instant.now(), t2 = Instant.EPOCH;
    System.out.printf("MILLIS %s%n", ChronoUnit.MILLIS.between(t2, t1));
    System.out.printf("SECONDS %s%n", ChronoUnit.SECONDS.between(t2, t1));
    System.out.printf("MINUTES %s%n", ChronoUnit.MINUTES.between(t2, t1));
    System.out.printf("HOURS %s%n", ChronoUnit.HOURS.between(t2, t1));
    System.out.printf("DAYS %s%n", ChronoUnit.DAYS.between(t2, t1));
  }

  /**
   * To define an amount of time with date-based values (years, months, days), use the Period class.
   * The Period class provides various get methods, such as getMonths, getDays, and getYears, so
   * that you can extract the amount of time from the period
   *
   * @since 2019年03月26日 19:33:56
   */
  @Test
  public void Period() {
    LocalDate today = LocalDate.now();
    LocalDate birthday = LocalDate.of(1995, Month.MARCH, 21);

    Period p = Period.between(birthday, today);
    System.out.printf("You are %s years %s months, and %s days old (%s days total)", p.getYears(),
        p.getMonths(), p.getDays(), ChronoUnit.DAYS.between(birthday, today));
  }
}
