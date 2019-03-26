package time;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.Test;

/**
 * @link https://docs.oracle.com/javase/tutorial/datetime/iso/timezones.html
 * @since 2019年03月21日 16:23:30
 **/
public class TimeZoneAndOffsetClasses {

  @Test
  public void ZoneIdAndZoneOffset() {
    // ZoneId specifies a time zone identifier and provides rules for converting between
    // an Instant and a LocalDateTime.
    // ZoneOffset specifies a time zone offset from Greenwich/UTC time.

    Set<String> allZones = ZoneId.getAvailableZoneIds();
    LocalDateTime dt = LocalDateTime.now();

    //Create a List using the set of zones and sort it.
    List<String> zoneList = new ArrayList<>(allZones);
    Collections.sort(zoneList);

    for (String s : zoneList) {
      ZoneId zone = ZoneId.of(s);
      ZonedDateTime zdt = dt.atZone(zone);
      ZoneOffset offset = zdt.getOffset();
      int secondsOfHour = offset.getTotalSeconds() % (60 * 60);
      if (secondsOfHour != 0) {
        String out = String.format("%20s %10s%n", zone, offset);
        System.out.println(out);
      }
    }
  }

  @Test
  public void ZonedDateTime() {
    /*
     * The ZonedDateTime class, in effect, combines the LocalDateTime class with the ZoneId class.
     * It is used to represent a full date (year, month, day) and time (hour, minute, second,
     * nanosecond) with a time zone (region/city, such as Europe/Paris)
     * */
    DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a");

    //Leaving from San Francisco on July 20, 2013, at 7:30 p.m.
    LocalDateTime leaving = LocalDateTime.of(2013, Month.JULY, 20, 19, 30);
    ZoneId leavingZone = ZoneId.of("America/Los_Angeles");
    ZonedDateTime departure = ZonedDateTime.of(leaving, leavingZone);

    try {
      String out1 = departure.format(format);
      System.out.printf("LEAVING: %s (%s)%n", out1, leavingZone);
    } catch (DateTimeException exc) {
      System.out.printf("%s can't be formatted!%n", departure);
      throw exc;
    }

    ZoneId arrivingZone = ZoneId.of("Asia/Tokyo");
    ZonedDateTime arrival = departure.withZoneSameInstant(arrivingZone).plusMinutes(650);

    try {
      String out2 = arrival.format(format);
      System.out.printf("ARRIVING: %s (%s)%n", out2, arrivingZone);
    } catch (DateTimeException exc) {
      System.out.printf("%s can't be formatted!%n", arrival);
      throw exc;
    }

    if (arrivingZone.getRules().isDaylightSavings(arrival.toInstant())) {
      System.out.printf(" (%s daylight saving time will be in effect.)%n", arrivingZone);
    } else {
      System.out.printf(" (%s standard time will be in effect.)%n", arrivingZone);
    }
  }

  /**
   * the OffsetDateTime class, in effect, combines the LocalDateTime class with the ZoneOffset
   * class. It is used to represent a full date (year, month, day) and time (hour, minute, second,
   * nanosecond) with an offset from Greenwich/UTC time (+/-hours:minutes, such as +06:00 or
   * -08:00)
   *
   * @since 2019年03月22日 09:55:43
   */
  @Test
  public void OffsetDateTime() {
    // Find the last Thursday in July 2018
    LocalDateTime localDate = LocalDateTime.of(2013, Month.JULY, 20, 19, 30);
    ZoneOffset offset = ZoneOffset.of("-08:00");

    OffsetDateTime offsetDate = OffsetDateTime.of(localDate, offset);
    OffsetDateTime lastThursday = offsetDate
        .with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
    System.out
        .printf("The last Thursday in July 2013 is the %sth,%n", lastThursday.getDayOfMonth());

  }
}
