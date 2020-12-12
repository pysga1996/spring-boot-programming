package vn.vissoft.ems.core.helper;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

public class RepoCommonUtil {

  public static <T> boolean getActive(T value) {
    if (value == null) {
      return false;
    }
    if (value instanceof Boolean) {
      return (Boolean) value;
    } else {
      if ((Integer) value == 1) {
        return true;
      } else {
        return false;
      }
    }
  }

  public static <T> Integer getIntegerByValue(T value) {
    if (value == null) {
      return null;
    }
    return Integer.parseInt(String.valueOf(value));
  }

  public static <T> String getStringByValue(T value) {
    if (value == null) {
      return null;
    }
    return String.valueOf(value);
  }

  public static <T> String getTimestampToString(T value, DateFormat dateFormat) {
    if (value == null) {
      return null;
    }
    Timestamp time = (Timestamp) value;
    return dateFormat.format(time);
  }

  public static <T> Date getTimestamp(T value) {
    if (value == null) {
      return null;
    }
    Timestamp time = (Timestamp) value;
    return new Date(time.getTime());
  }

  public static <T> Long getLongFromBigInteger(T value) {
    if (value == null) {
      return null;
    }
    return Long.parseLong(String.valueOf(value));
  }

  public static <T> Integer getIntegerFromBigInteger(T value) {
    if (value == null) {
      return null;
    }
    return Integer.parseInt(String.valueOf(value));
  }
}
