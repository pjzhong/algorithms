package time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.junit.Test;

/**
 * @link https://docs.oracle.com/javase/tutorial/datetime/iso/format.html
 * @since 2019年03月22日 11:09:15
 **/
public class ParsingAndFormatting {

  @Test
  public void parsing() {
    String input = "2019-03-22T03:47:26";
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
      LocalDate date = LocalDate.parse(input, formatter);
      System.out.printf("%s%n", date);
      System.out.println(Instant.now());
    } catch (DateTimeParseException exc) {
      System.out.printf("%s is not parsable!%n", input);
      throw exc;
    }
  }

  /**
   * @see TimeZoneAndOffsetClasses#ZonedDateTime()
   * @since 2019年03月22日 11:57:13
   */
  @Test
  public void customFormatting() {
    String input = "2019-03-27 15:09:47";
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      LocalDateTime date = LocalDateTime.parse(input, formatter);
      System.out.printf("%s%n", date);
      System.out.println(Instant.now());
    } catch (DateTimeParseException exc) {
      System.out.printf("%s is not parsable!%n", input);
      throw exc;
    }


  }
}
