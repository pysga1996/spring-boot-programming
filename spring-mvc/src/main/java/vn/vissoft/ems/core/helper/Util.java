package vn.vissoft.ems.core.helper;

import org.zkoss.chart.Charts;
import vn.vissoft.ems.core.dto.BsColorEnum;

import java.text.DateFormatSymbols;
import java.util.Random;

public class Util {

  public static Random random = new Random();
  public static String[] MONTHS = new DateFormatSymbols().getMonths();

  public static int nextInt(int low, int high) {
    return random.nextInt(high - low) + low;
  }

  public static void setupColor(Charts chart) {
    int n = chart.getSeriesSize();
    BsColorEnum[] colors = BsColorEnum.values();
    for (int i = 0; i < n; i++) {
      chart.getSeries(i).setColor(colors[i % colors.length].getHexCode());
    }
  }
}
