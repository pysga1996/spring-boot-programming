package vn.vissoft.ems.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vissoft.ems.core.model.Chart;
import vn.vissoft.ems.core.repo.ChartRepo;
import vn.vissoft.ems.core.services.ChartService;

import java.util.List;

@Service
public class ChartServiceImpl implements ChartService {

  @Autowired
  ChartRepo chartRepo;

  @Override
  public List<Chart> getDataChart() {
    try {
      List<Chart> charts = chartRepo.findAll();
      return charts;
    } catch (Exception e) {
      return null;
    }

  }

  @Override
  public String createData(Chart chart) {
    try {
      Chart chartDB = chartRepo.findByLegendAndCategory(chart.getLegend(), chart.getCategory());
      if (chartDB != null) {
        if (chartDB.getLegend().equals(chart.getLegend()) && chartDB.getCategory()
            .equals(chart.getCategory())) {
          chartDB.setValue(chart.getValue());
          chartDB.setLegend(chart.getLegend());
          chartDB.setCategory(chart.getCategory());
          chartRepo.save(chartDB);
        }
      } else {
        chartRepo.save(chart);
      }
      return "200";
    } catch (Exception e) {
      return null;
    }
  }
}
