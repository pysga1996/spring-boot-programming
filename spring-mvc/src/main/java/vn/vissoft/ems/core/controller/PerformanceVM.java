package vn.vissoft.ems.core.controller;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Series;
import org.zkoss.zk.ui.Component;
import vn.vissoft.ems.core.dto.BsColorEnum;
import vn.vissoft.ems.core.repo.ProjectDao;

public class PerformanceVM {

  private Charts chart;

  @Command
  public void init(@ContextParam(ContextType.COMPONENT) Component component) {
    chart = (Charts) component;

    Series series1 = chart.getSeries();
    series1.setColor(BsColorEnum.PRIMARY.getHexCode());
    series1.setName("Issues");
    chart.getPlotOptions().getAreaSpline().setFillOpacity(0.1);
    chart.getPlotOptions().getAreaSpline().getMarker().setEnabled(false);
    series1.setData(ProjectDao.queryIssues());
    Series series2 = chart.getSeries(1);
    series2.setColor(BsColorEnum.DANGER.getHexCode());
    series2.setName("Tasks");
    series2.setData(ProjectDao.queryTasks());
  }

}
