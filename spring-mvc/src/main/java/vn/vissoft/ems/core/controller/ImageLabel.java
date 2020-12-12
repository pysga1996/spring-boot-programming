package vn.vissoft.ems.core.controller;

import lombok.Data;
import org.zkoss.bind.annotation.*;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Legend;
import org.zkoss.chart.PlotLine;
import org.zkoss.chart.model.DefaultCategoryModel;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.*;
import vn.vissoft.ems.core.helper.Util;
import vn.vissoft.ems.core.model.Chart;
import vn.vissoft.ems.core.model.chart.DataChart;
import vn.vissoft.ems.core.services.ChartService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@VariableResolver(DelegatingVariableResolver.class)
public class ImageLabel extends Panel implements IdSpace, Composer {

  @Wire
  Charts chart;

  private DataChart dataChart;
  private String name;
  private List<String> types;
  private String type;
  private DefaultCategoryModel defaultCategoryModels;
  private static final long serialVersionUID = 6469233955454148948L;

  public void onCreate() {
    // 1. Render the template
    Map map = new HashMap<List<Object>, List<Object>>() {
      private static final long serialVersionUID = 7141348964577773718L;
    };

//        map.put("types", getTypes());
//        map.put("type", getType());
    map.put("dataChart", getDataChart());
    Executions.createComponents("/views/core/demo-chart/ImageLabel.zul",
        this, map);
//        System.out.println(dataChart);
    Selectors.wireVariables(this, this, null);
    Selectors.wireComponents(this, this, false);
    Selectors.wireEventListeners(this, this);

//        chart.getExporting().
  }

  public void setDataChart() {
    Execution execution = Executions.getCurrent();
//        List<String> a= (List<String>) execution.getArg().get("types");
    DataChart dataChart = (DataChart) execution.getArg().get("dataChart");
//        defaultCategoryModels = new DefaultCategoryModel();
//        for (int i = 0; i < charts.size(); i++) {
//            defaultCategoryModels.setValue(charts.get(i).getLegend(), charts.get(i).getCategory(), charts.get(i).getValue());
//        }
  }

  @Override
  public void doAfterCompose(Component comp) throws Exception {
//        createChart(comp);
////        chart.getExporting().
    this.comp = comp;
  }

  Component comp;

  public void createChart(Component comp) {
    setDataChart();
    Selectors.wireComponents(comp, this, false);
    chart.setModel(getDefaultCategoryModels());
//        chart.getXAxis().

    chart.getTitle().setX(-20);
    chart.getSubtitle().setX(-20);
    chart.getYAxis().setTitle("Temperature (°C)");
    PlotLine plotLine = new PlotLine();
    plotLine.setValue(0);
    plotLine.setWidth(1);
    plotLine.setColor("#808080");
//        PlotOptions plotOptions = new PlotOptions();
//        plotOptions.getLine().setLineWidth(80);
//        plotOptions.getLine().getMarker().setRadius(80);
    chart.getPlotOptions().getArea().getMarker().setEnabled(false);
    chart.getPlotOptions().getArea().setFillOpacity(0.1);
    chart.getPlotOptions().getLine().getMarker().setEnabled(false);
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


}
