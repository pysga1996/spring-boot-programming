package vn.vissoft.ems.core.controller;

import org.zkoss.bind.annotation.*;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Legend;
import org.zkoss.chart.PlotLine;
import org.zkoss.chart.plotOptions.PlotOptions;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;
import vn.vissoft.ems.core.helper.Util;
import vn.vissoft.ems.core.model.BasicChart;

import java.util.List;

public class DemoChartVM extends SelectorComposer<Window> {

  private String typeChart;
//    private Div div;

  @Wire
  Charts chart;

  public List<String> getTypes() {
    return BasicChart.getTypes();
  }

  @Init
  public void init() {
    setTypeChart("column");
  }

  public void setTypeChart(String typeChart) {
    this.typeChart = typeChart;
  }

  public String getTypeChart() {
    return typeChart;
  }

  @AfterCompose
  public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
    Selectors.wireComponents(view, this, false);
    chart.setModel(BasicChart.getCategoryModel());

    chart.getTitle().setX(-20);
    chart.getSubtitle().setX(-20);
    chart.getYAxis().setTitle("Temperature (°C)");
    PlotLine plotLine = new PlotLine();
    plotLine.setValue(0);
    plotLine.setWidth(1);
    plotLine.setColor("#808080");
    PlotOptions plotOptions = new PlotOptions();
    plotOptions.getLine().setLineWidth(0.8);
    plotOptions.getLine().getMarker().setRadius(0.8);
    chart.getPlotOptions().getAreaSpline().setFillOpacity(0.1);
    chart.getPlotOptions().getAreaSpline().getMarker().setEnabled(false);
    chart.getYAxis().addPlotLine(plotLine);
//        chart.addPlot(plotLine);
    chart.getTooltip().setValueSuffix("°C");

    Legend legend = chart.getLegend();
    legend.setLayout("vertical");
    legend.setAlign("right");
    legend.setVerticalAlign("middle");
    legend.setBorderWidth(0);
    Util.setupColor(chart);
  }

  @Command
  public void removeMail() {
    alert("Hello");
    System.out.println("hello");
  }
}
