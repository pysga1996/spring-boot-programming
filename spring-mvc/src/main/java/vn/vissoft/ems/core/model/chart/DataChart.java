package vn.vissoft.ems.core.model.chart;

import lombok.Data;

import java.util.List;

@Data
public class DataChart {

  private List<String> categories;
  private String chartType;
  private String title;
  private String subTitle;
  private List<Series> series;
}
