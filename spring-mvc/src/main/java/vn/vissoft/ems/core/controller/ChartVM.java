package vn.vissoft.ems.core.controller;


import com.google.gson.Gson;
import lombok.Data;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Legend;
import org.zkoss.chart.PlotLine;
import org.zkoss.chart.model.DefaultCategoryModel;
import org.zkoss.chart.plotOptions.PlotOptions;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkmax.ui.util.Toast;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Listbox;
import vn.vissoft.ems.core.helper.Util;
import vn.vissoft.ems.core.model.BasicChart;
import vn.vissoft.ems.core.model.Chart;
import vn.vissoft.ems.core.model.chart.DataChart;
import vn.vissoft.ems.core.model.chart.Point;
import vn.vissoft.ems.core.model.chart.Series;
import vn.vissoft.ems.core.services.ChartService;
import vn.vissoft.ems.core.services.impl.ChartServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Data
@VariableResolver(DelegatingVariableResolver.class)
public class ChartVM {

  @WireVariable
  ChartService chartServiceImpl;
  @Wire
  ImageLabel imageLabel;
  @Wire
  Charts chart;

  private List<Chart> charts;
  private Integer marker;
  private Chart chartLayout;
  private DataChart chartData = new DataChart();
  private List<DataChart> chartDatas;
  private JSONObject chartJson = new JSONObject();

  private List<String> types = new ArrayList<String>();
  private List<String> categories = new ArrayList<String>();

  public void setTypes() {
    types.add("Hà Nội");
    types.add("HCM");
    types.add("Đà Nẵng");
  }

  public void setCate() {
    categories.add("Tháng 1");
    categories.add("Tháng 2");
    categories.add("Tháng 3");
    categories.add("Tháng 4");
    categories.add("Tháng 5");
    categories.add("Tháng 6");
    categories.add("Tháng 7");
    categories.add("Tháng 8");
    categories.add("Tháng 9");
    categories.add("Tháng 10");
    categories.add("Tháng 11");
    categories.add("Tháng 12");
  }

  @Init
  public void init() {
    setTypes();
    setCate();
    setDataChart();
    String jsonInString = new Gson().toJson(chartData);
    chartJson = new JSONObject(jsonInString);
    charts = chartServiceImpl.getDataChart();
    chartLayout = new Chart();
  }

  public void setDataChart() {
    chartData.setCategories(new ArrayList<String>());
    chartData.getCategories().add("Tháng 1");
    chartData.getCategories().add("Tháng 2");
    chartData.getCategories().add("Tháng 3");
    chartData.setTitle("Column chart Demo");
    chartData.setSubTitle("demo");
    chartData.setChartType("column");
    chartData.setSeries(new ArrayList<Series>());

    List<Point> points1 = new ArrayList<>();
    points1.add(new Point(22, "Tháng 1"));
    points1.add(new Point(44, "Tháng 2"));
    Series series1 = new Series();
    series1.setLegend("Hà Nội");
    series1.setChartType("line");
    series1.setPoints(points1);
    chartData.getSeries().add(series1);

    List<Point> points2 = new ArrayList<>();
    points2.add(new Point(20, "Tháng 1"));
    points2.add(new Point(60, "Tháng 2"));
    Series series2 = new Series();
    series2.setLegend("HCM");
    series2.setChartType("line");
    series2.setPoints(points2);
    chartData.getSeries().add(series2);

    chartDatas = new ArrayList<DataChart>();
    chartDatas.add(chartData);
  }

  @Command
  @NotifyChange({"chartLayout", "charts", "types", "imageLabel"})
  public void createValue() {
    types.add("AAA");
//        String result = chartServiceImpl.createData(chartLayout);
//        System.out.println(result);
//        if (result == "200") {
//            Toast.show("create successful.");
//            chartLayout.setValue(null);
//            chartLayout.setCategory(null);
//            chartLayout.setLegend(null);
//            setCharts(chartServiceImpl.getDataChart());
////            this.imageLabel.createChart();
////            BindUtils.postGlobalCommand(null,null,"createChart",null);
//        }
  }

  @Command
  @NotifyChange("types")
  public void delete(Listbox listbox) {
    System.out.println(listbox.getSelectedIndex());
//        types.remove(0);

  }

  @GlobalCommand
  public void testChart() {
    System.out.println("OKOOKOKOKOk");
  }

  @Command
  @NotifyChange("marker")
  public void checkMarker(@BindingParam("checkMarker") Integer marker) {
    setMarker(marker);
    if (marker == 2) {
      chart.getPlotOptions().getLine().getMarker().setEnabled(false);
    } else {
      chart.getPlotOptions().getLine().getMarker().setEnabled(true);
    }
  }
}
