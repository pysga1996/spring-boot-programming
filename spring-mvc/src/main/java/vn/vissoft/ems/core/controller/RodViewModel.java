package vn.vissoft.ems.core.controller;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import vn.vissoft.ems.core.dto.LiveOrderListDTO;
import vn.vissoft.ems.core.dto.OrderListDTO;
import vn.vissoft.ems.core.model.Order;
import vn.vissoft.ems.core.repo.OrderDao;

import java.util.List;

/**
 * @author Hawk
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class RodViewModel {

  @WireVariable
  private OrderDao springOrderDao;

  private List<Order> orders;

  private OrderListDTO orderListModel;

  private LiveOrderListDTO liveOrderListModel;

  @Init
  public void init() {
    orders = springOrderDao.getAll();
    orderListModel = new OrderListDTO(orders, springOrderDao);
    liveOrderListModel = new LiveOrderListDTO(springOrderDao);
  }


  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }


  public OrderListDTO getOrderListModel() {
    return orderListModel;
  }


  public void setOrderListModel(OrderListDTO orderListModel) {
    this.orderListModel = orderListModel;
  }


  public LiveOrderListDTO getLiveOrderListModel() {
    return liveOrderListModel;
  }


  public void setLiveOrderListModel(LiveOrderListDTO liveOrderListModel) {
    this.liveOrderListModel = liveOrderListModel;
  }

}
