package vn.vissoft.ems.core.model.chart;

import lombok.Data;

import java.util.List;

@Data
public class Series {

  private String chartType;
  private String legend;
  private List<Point> points;
}
