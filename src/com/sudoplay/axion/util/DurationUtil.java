package com.sudoplay.axion.util;

public class DurationUtil {

  private static final long MILLIS_PER_SECOND = 1000;
  private static final long MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60;
  private static final long MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
  private static final long MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;
  private static final long MILLIS_PER_WEEK = MILLIS_PER_DAY * 7;
  private static final long MILLIS_PER_YEAR = MILLIS_PER_WEEK * 52;

  private DurationUtil() {
    //
  }

  public static String formatDurationWords(long durationMillis) {

    int years = 0;
    int weeks = 0;
    int days = 0;
    int hours = 0;
    int minutes = 0;
    int seconds = 0;
    int milliseconds = 0;
    
    years = (int) (durationMillis / MILLIS_PER_YEAR);
    durationMillis -= years * MILLIS_PER_YEAR;
    
    weeks = (int) (durationMillis / MILLIS_PER_WEEK);
    durationMillis -= weeks * MILLIS_PER_WEEK;

    days = (int) (durationMillis / MILLIS_PER_DAY);
    durationMillis -= days * MILLIS_PER_DAY;

    hours = (int) (durationMillis / MILLIS_PER_HOUR);
    durationMillis -= hours * MILLIS_PER_HOUR;

    minutes = (int) (durationMillis / MILLIS_PER_MINUTE);
    durationMillis -= minutes * MILLIS_PER_MINUTE;

    seconds = (int) (durationMillis / MILLIS_PER_SECOND);
    durationMillis -= seconds * MILLIS_PER_SECOND;
    
    milliseconds = (int) durationMillis;

    StringBuilder out = new StringBuilder();

    if (years > 0) {
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(years);
      out.append(" year");
      if (years > 1) {
        out.append("s");
      }
    }

    if (weeks > 0) {
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(weeks);
      out.append(" week");
      if (weeks > 1) {
        out.append("s");
      }
    }

    if (days > 0) {
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(days);
      out.append(" day");
      if (days > 1) {
        out.append("s");
      }
    }

    if (hours > 0) {
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(hours);
      out.append(" hour");
      if (hours > 1) {
        out.append("s");
      }
    }

    if (minutes > 0) {
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(minutes);
      out.append(" minute");
      if (minutes > 1) {
        out.append("s");
      }
    }

    if (seconds > 0) {
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(seconds);
      out.append(" second");
      if (seconds > 1) {
        out.append("s");
      }
    }

    if (milliseconds > 0) {
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(milliseconds);
      out.append(" millisecond");
      if (milliseconds > 1) {
        out.append("s");
      }
    }

    return out.toString();

  }

}
