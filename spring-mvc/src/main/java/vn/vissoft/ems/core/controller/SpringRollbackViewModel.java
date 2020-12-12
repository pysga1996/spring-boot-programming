package vn.vissoft.ems.core.controller;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import vn.vissoft.ems.core.model.Order;
import vn.vissoft.ems.core.repo.OrderDao;

import java.util.Calendar;
import java.util.List;

/**
 * Test when a exception happens during DAO save an object, filter will perform rollback.
 *
 * @author Hawk
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SpringRollbackViewModel {

  @WireVariable
  private OrderDao springOrderDao;


  private List<Order> orders;
  private boolean error = false;

  @Init
  public void init() {
    orders = springOrderDao.getAll();
  }

  @Command
  @NotifyChange("orders")
  public void add() {
    Order newOrder = new Order();
    newOrder.setDescription("auto add " + Calendar.getInstance().getTime());
    if (error) {
      springOrderDao.errorSave(newOrder);
    } else {
      springOrderDao.save(newOrder);
    }
    orders = springOrderDao.getAll();
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public boolean isError() {
    return error;
  }

  public void setError(boolean error) {
    this.error = error;
  }
}
