package vn.vissoft.ems.core.controller;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Color;
import org.zkoss.chart.Point;
import org.zkoss.chart.Series;
import org.zkoss.chart.plotOptions.PiePlotOptions;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import vn.vissoft.ems.core.dto.BsColorEnum;
import vn.vissoft.ems.core.dto.ProductDTO;
import vn.vissoft.ems.core.repo.EcommerceDao;

import java.util.List;

public class MarketshareVM {

  private List<ProductDTO> productList;
  @Wire
  Charts chart;


  @AfterCompose
  public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
    Selectors.wireComponents(view, this, false);

    productList = EcommerceDao.queryProduct();
    chart.getPlotOptions().getPie().setShadow(false);
    chart.getPlotOptions().getPie().setCenter("50%", "50%");
    chart.getPlotOptions().getPie().setInnerSize("50%"); //make center hollow
    initSeries();
  }

  private void initSeries() {
    Series marketShare = chart.getSeries();
    marketShare.setName("Products");
    List<Color> colors = chart.getColors();
    int i = 0;
    for (ProductDTO product : productList) {
      String name = product.getName();
      double amount = product.getAmount();
      Point productMarketShare = new Point(name, amount);
      productMarketShare.setColor(colors.get(i));
      productMarketShare.getDataLabels().setEnabled(false);
      marketShare.addPoint(productMarketShare);
      i += 1;
      productMarketShare.setColor(BsColorEnum.getColor(i).getHexCode());
    }
    PiePlotOptions plotOptions = new PiePlotOptions();
    plotOptions.setShowInLegend(true);
    marketShare.setPlotOptions(plotOptions);
  }

  public List<ProductDTO> getProductList() {
    return productList;
  }
}
