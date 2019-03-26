package time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @link https://docs.oracle.com/javase/tutorial/datetime/iso/instant.html
 * @since 2019年03月22日 10:15:41
 **/
public class InstantClass {

  @Test
  public void instant() {
    Instant timestamp = Instant.now();
    System.out.println(timestamp);

    Instant oneHourLater = Instant.now().plusSeconds(TimeUnit.HOURS.toSeconds(1));
    System.out.println(oneHourLater);

    long secondsFromEpoch = Instant.ofEpochSecond(0).until(Instant.now(), ChronoUnit.SECONDS);
    System.out.println(secondsFromEpoch);

    LocalDateTime ldt = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault());
    System.out.printf("%s %d %d at %d:%d%n", ldt.getMonth(), ldt.getDayOfMonth(), ldt.getYear(),
        ldt.getHour(), ldt.getMinute());
  }
}
