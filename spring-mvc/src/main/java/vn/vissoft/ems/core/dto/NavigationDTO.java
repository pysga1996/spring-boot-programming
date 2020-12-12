package vn.vissoft.ems.core.dto;

public class NavigationDTO {

  public static final String DASHBOARD_ECOMMERCE_ZUL = "ecommerce/ecommerce.zul";
  public static final String DASHBOARD_PROJECT_ZUL = "project/project.zul";
  public static final String BLANK_ZUL = "blank.zul";
  public static final String DEMO_CHART = "core/demo-chart/baseChart.zul";
  public static final String DEMO_TABLE = "core/demo-table/baseTable.zul";

  private String contentUrl = DASHBOARD_ECOMMERCE_ZUL;

  public String getContentUrl() {
    return contentUrl;
  }

  public void setContentUrl(String contentUrl) {
    this.contentUrl = contentUrl;
  }
}
