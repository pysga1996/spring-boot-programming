package vn.vissoft.ems.core.controller;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Legend;
import org.zkoss.chart.PlotLine;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import vn.vissoft.ems.core.helper.Util;
import vn.vissoft.ems.core.repo.EcommerceDao;

public class RevenueVM {

  @Wire
  Charts chart;

  @AfterCompose
  public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
    Selectors.wireComponents(view, this, false);
    chart.setModel(EcommerceDao.getRevenueModel());

    chart.getTitle().setX(-20);
    chart.getSubtitle().setX(-20);
    chart.getYAxis().setTitle("dollars");
    PlotLine plotLine = new PlotLine();
    plotLine.setValue(0);
    plotLine.setWidth(1);
    plotLine.setColor("#808080");
    chart.getPlotOptions().getAreaSpline().setFillOpacity(0.1);
    chart.getPlotOptions().getAreaSpline().getMarker().setEnabled(false);
    chart.getYAxis().addPlotLine(plotLine);
    chart.getTooltip().setValueSuffix("$");

    Legend legend = chart.getLegend();
    legend.setLayout("vertical");
    legend.setAlign("right");
    legend.setVerticalAlign("middle");
    legend.setBorderWidth(0);
    Util.setupColor(chart);
  }
}
