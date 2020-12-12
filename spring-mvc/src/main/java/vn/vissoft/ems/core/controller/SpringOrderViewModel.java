package vn.vissoft.ems.core.controller;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import vn.vissoft.ems.core.model.Order;
import vn.vissoft.ems.core.repo.OrderDao;

import java.util.List;

/**
 * @author Hawk
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SpringOrderViewModel {

  @WireVariable
  private OrderDao springOrderDao;

  private List<Order> orders;
  private Order selectedItem;

  @Init
  public void init() {
    orders = springOrderDao.getAll();
    if (!orders.isEmpty()) {
      setSelectedItem(orders.get(0));
    }
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public Order getSelectedItem() {
    if (selectedItem != null) {
      selectedItem = springOrderDao.reload(selectedItem);
      //you could replace the item in model list with initialized one
    }
    return selectedItem;
  }

  public void setSelectedItem(Order selectedItem) {
    this.selectedItem = selectedItem;
  }

}
