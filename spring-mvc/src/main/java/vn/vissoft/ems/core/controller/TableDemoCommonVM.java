package vn.vissoft.ems.core.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import vn.vissoft.ems.core.dto.ColumnDTO;
import vn.vissoft.ems.core.helper.AppConst;
import vn.vissoft.ems.core.model.User;
import vn.vissoft.ems.core.services.UsersService;

import java.util.*;

public class TableDemoCommonVM extends AbsCommonVM {

  @WireVariable
  private UsersService usersService;

  // search
  private String userName;

  // filter
  private List<FilterObj> filterObjList;
  private FilterObj filterObj;

  // update
  private StringBuilder sqlUpdate;
  private List<Object> paramUpdate;

  public TableDemoCommonVM() {
    super("Users", TableDemoCommonVM.class, User.class);
  }

  @Init
  public void init() {
    // TODO
    this.getFilterDefault();
    // super
    super.init();
  }

  @Override
  protected void initColsModel() {
    this.setColsModel(
        Arrays.asList(
            new ColumnDTO("user.code", "code", "code", "left", "150px", true, true),
            new ColumnDTO("user.email", "email", "email", "left", "150px", true, false),
            new ColumnDTO("user.userName", "user_name", "userName", "left", "150px", true, false),
            new ColumnDTO("user.locked", "is_locked", "locked", "center", "150px", true, true),
            new ColumnDTO("user.createdAt", "created_at", "createdAt", "right", "150px", true,
                true),
            new ColumnDTO("user.updatedAt", "updated_at", "updatedAt", "right", "150px", true, true)
        )
    );
  }

  @Override
  protected void getActionDefault() {
    this.setActionObjList(
        Arrays.asList(/*z-icon-check-square-o*/
            new ActionObj("index.action.delete", "z-icon-times", "", "", "", "doDelete"),
            new ActionObj("index.action.update", "z-icon-pencil", "", "", "", "doUpdate")
        )
    );
  }

  @Command
  @NotifyChange({"listModel", "totalSize", "activePage", "activeSize"})
  public void doFilter(@BindingParam("box") Combobox box) {
    String value = box.getSelectedItem().getValue();
    if (AppConst.STATUS_FILTER.ACTIVATED.equals(value)) {
      this.setSqlFilter(" AND is_locked = 0 ");
    } else if (AppConst.STATUS_FILTER.NOT_ACTIVATED.equals(value)) {
      this.setSqlFilter(" AND is_locked = 1 ");
    } else {
      this.setSqlFilter(" ");
    }

    this.doSearch();
  }


  @Command
  @NotifyChange({"listModel", "totalSize", "activePage", "activeSize"})
  public void doDelete(@ContextParam(ContextType.BINDER) final Binder binder,
      @BindingParam("row") Object row) {
    System.out.println(row);
    Messagebox.show("Do you really want to delete as " + ((User) row).getUserName(),
        new Messagebox.Button[]{Messagebox.Button.YES
            , Messagebox.Button.CANCEL}, new EventListener<Messagebox.ClickEvent>() {
          public void onEvent(Messagebox.ClickEvent event) throws Exception {
            if (event.getButton() == Messagebox.Button.YES) {
              usersService.deleteById(((User) row).getUserId());
              doSearch();

              Map<String, Object> map = new HashMap<>();
              map.put("userName", ((User) row).getUserName());
              binder.postCommand("delSuccessMsg", map);
            }
          }
        });

  }

  @Command
  public void delSuccessMsg(@BindingParam("userName") Object userName) {
    Clients.showNotification("Delete success, " + userName, false);
  }

  @Command
  @NotifyChange({"listModel", "totalSize", "activePage", "activeSize"})
  public void doUpdate(@BindingParam("row") Object row) {
    System.out.println(row);
    User user = (User) row;
    sqlUpdate = new StringBuilder("UPDATE " + this.TABLE_NAME + " SET ");
    paramUpdate = new ArrayList<>();

    if (user.getUserName() != null) {
      sqlUpdate.append(" user_name =?1, ");
      paramUpdate.add(user.getUserName());
    }
    if (user.getUserName() != null) {
      sqlUpdate.append(" email =?2, ");
      paramUpdate.add(user.getEmail());
    }

    sqlUpdate.append(" is_locked =?3 ");
    paramUpdate.add(user.isLocked());
    // where
    sqlUpdate.append(" WHERE user_id = ?4 ");
    paramUpdate.add(user.getUserId());
    if (this.doUpdateRow(sqlUpdate.toString(), paramUpdate)) {
      // restart
      this.doSearch();
    }

  }

  @Override
  protected void setSqlWhereAndParams() {
    StringBuilder sql = new StringBuilder(" WHERE 1=1 ");
    List<Object> params = new ArrayList<>();
    if (!StringUtils.isEmpty(this.getUserName())) {
      sql.append(" AND UPPER(user_name) LIKE UPPER(?1) ");
      params.add("%" + this.getUserName() + "%");
    }
    this.setSqlWhere(sql);
    this.setParams(params);
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public FilterObj getFilterObj() {
    return filterObj;
  }

  public void setFilterObj(FilterObj filterObj) {
    this.filterObj = filterObj;
  }

  public List<FilterObj> getFilterObjList() {
    return filterObjList;
  }

  public void setFilterObjList(List<FilterObj> filterObjList) {
    this.filterObjList = filterObjList;
  }

  private void getFilterDefault() {
    this.setFilterObjList(
        Arrays.asList(
            new FilterObj("index.filter.all", AppConst.STATUS_FILTER.ALL),
            new FilterObj("index.filter.active", AppConst.STATUS_FILTER.ACTIVATED),
            new FilterObj("index.filter.inActive", AppConst.STATUS_FILTER.NOT_ACTIVATED)
        )
    );
    this.setFilterObj(this.getFilterObjList().get(0));
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public class FilterObj {

    private String title;
    private String value;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public class ActionObj {

    private String title;
    private String icon;
    private String pathImg;
    private String width;
    private String autoDisable;
    private String funcName;

  }
}
