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

    StringBuilder out = new StringBuilder();

    if (durationMillis >= MILLIS_PER_YEAR) {
      years = (int) (durationMillis / MILLIS_PER_YEAR);
      durationMillis -= years * MILLIS_PER_YEAR;
      out.append(years);
      out.append(" year");
      if (years > 1) {
        out.append("s");
      }
    }

    if (durationMillis >= MILLIS_PER_WEEK) {
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(weeks);
      out.append(" week");
      if (weeks > 1) {
        out.append("s");
      }
      weeks = (int) (durationMillis / MILLIS_PER_WEEK);
      durationMillis -= weeks * MILLIS_PER_WEEK;
    }

    if (durationMillis >= MILLIS_PER_DAY) {
      days = (int) (durationMillis / MILLIS_PER_DAY);
      durationMillis -= days * MILLIS_PER_DAY;
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(days);
      out.append(" day");
      if (days > 1) {
        out.append("s");
      }
    }

    if (durationMillis >= MILLIS_PER_HOUR) {
      hours = (int) (durationMillis / MILLIS_PER_HOUR);
      durationMillis -= hours * MILLIS_PER_HOUR;
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(hours);
      out.append(" hour");
      if (hours > 1) {
        out.append("s");
      }
    }

    if (durationMillis >= MILLIS_PER_MINUTE) {
      minutes = (int) (durationMillis / MILLIS_PER_MINUTE);
      durationMillis -= minutes * MILLIS_PER_MINUTE;
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(minutes);
      out.append(" minute");
      if (minutes > 1) {
        out.append("s");
      }
    }

    if (durationMillis >= MILLIS_PER_SECOND) {
      seconds = (int) (durationMillis / MILLIS_PER_SECOND);
      durationMillis -= seconds * MILLIS_PER_SECOND;
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(seconds);
      out.append(" second");
      if (seconds > 1) {
        out.append("s");
      }
    }

    if (durationMillis > 0) {
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(durationMillis);
      out.append(" millisecond");
      if (durationMillis > 1) {
        out.append("s");
      }
    }

    return out.toString();

  }

}
