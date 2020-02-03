package me.swang.springplayground;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.lang.System.out;

public class TimeZoneTests {

  private static final String dateStampStr = "2020-02-03 10:01:00";
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // y: year, M:
                                                                                                  // Month, d: day,
                                                                                                  // H: Hour,
  // m: minute, s: second, S: milliSecond

  public static void main(String[] args) {
    timeZoneConverter();
  }

  /**
   * SimpleDateFormat will parse the string with time zone info, either from the
   * time zone of the string, or from the time zone of the SimpleDateFormat. In
   * the example, the time is 9:01, if no time zone is set on the simple date
   * format, it is always is EST. No matter what time zone is set on the Calendar,
   * the time is always 9:01 EST. But if the time zone of Simple Date Format is
   * set to be central, when 9:01 is parsed and set to the Calendar, the time will
   * be 10:01 EST, because 9:01 is central when Simple Date Format parses it.
   * Summarize: Time Zone can only be changed/set on the source string by 'z'
   * (yyyy-MM-dd HH:mm:ss SSS z) or SimpleDateFormat; Calendar's Time Zone is
   * always the Time Zone of JVM, even Calendar.setTimeZone is called with
   * different time zone, when it will be overriden when setTime is called.
   */
  public static void timeZoneConverter() {

    Calendar now = Calendar.getInstance(); // without passing time zone, it would be default of eastern standard time
    TimeZone nowTimeZone = now.getTimeZone(); // get time zone of now
    out.printf("Time zone of now is %s\n", nowTimeZone.getDisplayName());

    // dateFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));

    out.printf("Simple date format time zone is %s\n", dateFormat.getTimeZone().getDisplayName());
    Date date = null;
    try {
      date = dateFormat.parse(dateStampStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    now.setTime(date);

    out.printf("Date stamp parsed is %s\n", date);
    out.printf("Time of now is %s\n", now.getTime());

    /**
     * Below code only changes the DateFormat to be in central time zone, but the
     * calendar is still in EST, that is why the time is one hour ahead, 10:01
     * Central is 11:01 EST.
     */
    out.println("Trying to change the date format to Central Standard Time ->");

    SimpleDateFormat centralDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    centralDateFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
    Calendar centralNow = Calendar.getInstance();

    Date centralDate = null;
    try {
      centralDate = centralDateFormat.parse(dateStampStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    centralNow.setTime(centralDate);

    // 09:01:00 is expected
    out.println("09:01:00 is expected since the time zone is central.");
    out.printf("Time of central is %s and time zone is %s\n", centralNow.getTime(),
        centralNow.getTimeZone().getDisplayName());

    // now we try to change the time zone of calendar instance, when we setTime, the
    // timezone will be overridden
    Calendar centralCalendarNow = Calendar.getInstance(TimeZone.getTimeZone("US/Central"));
    out.printf("After setting central time zone the calendar has %s zone\n",
        centralCalendarNow.getTimeZone().getDisplayName());
    centralCalendarNow.setTime(date);
    out.printf("After setting time the calendar has %s zone\n", centralCalendarNow.getTimeZone().getDisplayName());
    out.println("09:01:00 is expected since the time zone is central.");
    // summary is that set time zone on Calendar will not override the time zone of
    // time value.
    out.printf("Time of central is %s and time zone is %s\n", centralCalendarNow.getTime(),
        centralCalendarNow.getTimeZone().getDisplayName());
  }
}