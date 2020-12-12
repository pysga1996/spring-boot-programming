package vn.vissoft.ems.core.repo;

import vn.vissoft.ems.core.model.Order;

import java.util.List;


public interface OrderDao extends BaseDao<Order> {

  public void errorSave(Order newOrder);

  public Order reload(Order order);

  public List<Order> queryAll(int offset, int max);

  public Long queryAllSize();
}
