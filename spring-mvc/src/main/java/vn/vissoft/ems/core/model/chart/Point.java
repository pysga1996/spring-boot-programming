package vn.vissoft.ems.core.model.chart;

import lombok.Data;

@Data
public class Point {

  private Integer value;
  private String category;

  public Point(Integer value, String category) {
    this.value = value;
    this.category = category;
  }
}
