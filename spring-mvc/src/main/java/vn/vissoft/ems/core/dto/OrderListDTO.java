package vn.vissoft.ems.core.dto;

import org.zkoss.zul.AbstractListModel;
import vn.vissoft.ems.core.model.Order;
import vn.vissoft.ems.core.repo.OrderDao;

import java.util.LinkedList;
import java.util.List;

/**
 * Simple solution for lazy initialization issue under Render On Demand. It's simple but has worse
 * performance for redundant queries.
 *
 * @author Hawk
 */
public class OrderListDTO extends AbstractListModel<Order> {

  private static final long serialVersionUID = -7982684413905984053L;

  private OrderDao orderDao;
  List<Order> orderList = new LinkedList<Order>();

  public OrderListDTO(List<Order> orders, OrderDao orderDao) {
    this.orderList = orders;
    this.orderDao = orderDao;
  }

  @Override
  public Order getElementAt(int index) {
    //throw a runtime exception if orderDao does not find target object
    Order renewOrder = orderDao.reload(orderList.get(index));
    return renewOrder;
  }

  @Override
  public int getSize() {
    return orderList.size();
  }
}
