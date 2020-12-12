package vn.vissoft.ems.core.controller;

import org.zkoss.bind.annotation.Init;
import vn.vissoft.ems.core.dto.ProductDTO;
import vn.vissoft.ems.core.repo.EcommerceDao;

import java.util.List;

public class SaleVM {

  private List<ProductDTO> productList;

  @Init
  public void init() {
    productList = EcommerceDao.queryProduct();
  }

  public List<ProductDTO> getProductList() {
    return productList;
  }
}
