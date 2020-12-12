package vn.vissoft.ems.core.repo;

import org.zkoss.chart.model.CategoryModel;
import org.zkoss.chart.model.ChartsModel;
import org.zkoss.chart.model.DefaultCategoryModel;
import vn.vissoft.ems.core.dto.ProductDTO;
import vn.vissoft.ems.core.helper.Util;

import java.util.LinkedList;
import java.util.List;

/**
 * Data access object
 */
public class EcommerceDao {

  private static CategoryModel revenueModel;
  private static CategoryModel productImportModel;
  private static List<ProductDTO> productList = new LinkedList<>();

  public static String[] PRODUCT_NAMES = {"Charts", "Framework", "Theme Pack", "Spreadsheet",
      "Calendar", "Pivot Table"};

  static {
    revenueModel = new DefaultCategoryModel();
    for (String month : Util.MONTHS) {
      revenueModel.setValue(PRODUCT_NAMES[1], month, Util.random.nextInt(10000));
      revenueModel.setValue(PRODUCT_NAMES[3], month, Util.random.nextInt(10000));
    }

    productImportModel = new DefaultCategoryModel();
    for (String month : Util.MONTHS) {
      productImportModel.setValue(PRODUCT_NAMES[3], month, Util.random.nextInt(10000));
      productImportModel.setValue(PRODUCT_NAMES[4], month, Util.random.nextInt(10000));
    }

    for (String name : PRODUCT_NAMES) {
      ProductDTO product = new ProductDTO(name);
      product.setQuantity(Util.nextInt(20, 100));
      product.setPrice(Util.nextInt(100, 1000) / 10);
      productList.add(product);
    }
  }

  public static CategoryModel getRevenueModel() {
    return revenueModel;
  }

  public static List<ProductDTO> queryProduct() {
    return productList;
  }

  public static ChartsModel getProductImportData() {
    return productImportModel;
  }
}
