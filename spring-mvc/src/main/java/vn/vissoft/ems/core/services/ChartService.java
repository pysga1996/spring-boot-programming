package vn.vissoft.ems.core.services;

import org.zkoss.json.JSONObject;
import vn.vissoft.ems.core.model.Chart;
import vn.vissoft.ems.core.model.chart.DataChart;

import java.util.List;

public interface ChartService {

  List<Chart> getDataChart();

  String createData(Chart chart);

  DataChart chartData = new DataChart();
}
