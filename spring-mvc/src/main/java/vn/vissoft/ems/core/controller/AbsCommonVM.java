package vn.vissoft.ems.core.controller;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.event.PagingEvent;
import vn.vissoft.ems.core.dto.ColumnDTO;
import vn.vissoft.ems.core.services.AbsCommonService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@VariableResolver(DelegatingVariableResolver.class)
public abstract class AbsCommonVM {

  // description class model
  protected final Class<?> CONTROLLER_CLASS;
  protected final Class<?> MODEL_CLASS;

  // description data model
  protected List<ColumnDTO> colsModel;
  protected ListModelList<?> listModel;
  protected List<?> currentModel = new ArrayList<>();

  // block paging
  protected boolean pageDetailed = true;
  protected int totalSize;
  protected int activeSize;
  protected int activePage;
  protected List<Integer> pageSizeOption;
  protected boolean showIndex = true;

  // block action
  protected boolean showAction = false;
  protected List<?> actionObjList = null;

  // block search
  protected final String TABLE_NAME;
  protected StringBuilder sqlWhere = new StringBuilder(" WHERE 1=1 ");
  protected StringBuilder sqlSelect = new StringBuilder(" SELECT * FROM ");
  protected String sqlOrderBy = "";
  protected String sqlFilter = "";

  protected List<Object> params = new ArrayList<>();

  @WireVariable
  private AbsCommonService absCommonService;

  protected AbsCommonVM(final String tableName, final Class<?> ctrClass,
      final Class<?> modelClass) {
    TABLE_NAME = tableName;
    CONTROLLER_CLASS = ctrClass;
    MODEL_CLASS = modelClass;
  }

  // start - block action init
  protected void init() {
    // set default
    this.setPageSizeOptionDefault();

    // column
    initColsModel();
    // action
    getActionDefault();
    if (this.actionObjList != null) {
      this.showAction = true;
    }
    // init data
    this.getData();
  }

  /*protected void initColsModel() {
      this.setColsModel(new ArrayList<ColumnDTO>());

      Field[] fields = MODEL_CLASS.getDeclaredFields();
      if (fields != null) {
          for (Field fd:fields) {
              // model obj
              this.getColsModel().add(new ColumnDTO(
                      MODEL_CLASS.getSimpleName().toLowerCase() + "." + fd.getName(),
                      fd.getName(),
                      fd.getName(),
                      true));
          }
      }
  }*/
  protected abstract void initColsModel();

  protected abstract void getActionDefault();

  protected abstract void setSqlWhereAndParams();
  // end - block action init

  public boolean doUpdateRow(String sqlUpdate, List<Object> params) {
    return absCommonService.updateRow(sqlUpdate, params);
  }

  protected void getData() {
    StringBuilder sqlQuery = new StringBuilder("");
    StringBuilder sqlWhereAndFilter = new StringBuilder("");
    sqlWhereAndFilter.append(this.sqlWhere).append(this.sqlFilter);
    sqlQuery.append(this.sqlSelect)
        .append(TABLE_NAME)
        .append(sqlWhereAndFilter)
        .append(this.sqlOrderBy);
    currentModel = absCommonService.searchPaging(
        MODEL_CLASS, TABLE_NAME,
        sqlQuery.toString(), this.params,
        this.getActivePage() + 1, this.getActiveSize());
    totalSize = absCommonService.countPage(TABLE_NAME, sqlWhereAndFilter.toString(), this.params);
    this.listModel = new ListModelList<Object>(currentModel);
  }

  public ListModel<?> getListModel() {
    return listModel;
  }

  @Command
  @NotifyChange({"listModel", "totalSize", "activePage", "activeSize"})
  public void doSearch() {
    this.setSqlWhereAndParams();
    this.getData();
  }

  @Command
  @NotifyChange({"listModel", "totalSize", "activePage", "activeSize"})
  public void doPaging(@BindingParam("event") PagingEvent event) {
    this.setActiveSize(event.getPageable().getPageSize());
    this.setActivePage(event.getActivePage());
    this.getData();
  }

  @Command
  @NotifyChange({"listModel", "totalSize", "activePage", "activeSize"})
  public void doPageSize() {
    this.setActivePage(0);
    this.getData();
  }

  @Command
  @NotifyChange({"listModel", "totalSize", "activePage", "activeSize"})
  public void doSort(@BindingParam("col") ColumnDTO column) {
    if (column.isSortASC()) {
      this.setSqlOrderBy(" ORDER BY " + column.getColInDB() + " ASC ");
    } else {
      this.setSqlOrderBy(" ORDER BY " + column.getColInDB() + " DESC ");
    }
    column.setSortASC(!column.isSortASC()); // set status sort column lan 2
    this.getData();
  }

  public StringBuilder getSqlWhere() {
    return sqlWhere;
  }

  public void setSqlWhere(StringBuilder sqlWhere) {
    this.sqlWhere = sqlWhere;
  }

  public StringBuilder getSqlSelect() {
    return sqlSelect;
  }

  public void setSqlSelect(StringBuilder sqlSelect) {
    this.sqlSelect = sqlSelect;
  }

  public String getSqlOrderBy() {
    return sqlOrderBy;
  }

  public void setSqlOrderBy(String sqlOrderBy) {
    this.sqlOrderBy = sqlOrderBy;
  }

  public String getSqlFilter() {
    return sqlFilter;
  }

  public void setSqlFilter(String sqlFilter) {
    this.sqlFilter = sqlFilter;
  }

  public List<Object> getParams() {
    return params;
  }

  public void setParams(List<Object> params) {
    this.params = params;
  }

  public List<ColumnDTO> getColsModel() {
    return colsModel;
  }

  public void setColsModel(List<ColumnDTO> colsModel) {
    this.colsModel = colsModel;
  }

  public boolean isShowIndex() {
    return showIndex;
  }

  public void setShowIndex(boolean showIndex) {
    this.showIndex = showIndex;
  }

  public boolean isShowAction() {
    return showAction;
  }

  public void setShowAction(boolean showAction) {
    this.showAction = showAction;
  }

  public List<?> getActionObjList() {
    return actionObjList;
  }

  public void setActionObjList(List<?> actionObjList) {
    this.actionObjList = actionObjList;
  }

  public int getTotalSize() {
    return totalSize;
  }

  public void setTotalSize(int totalSize) {
    this.totalSize = totalSize;
  }

  public int getActiveSize() {
    return activeSize;
  }

  public void setActiveSize(int activeSize) {
    this.activeSize = activeSize;
  }

  public int getActivePage() {
    return activePage;
  }

  public void setActivePage(int activePage) {
    this.activePage = activePage;
  }

  public boolean getPageDetailed() {
    return pageDetailed;
  }

  public void setPageDetailed(boolean pageDetailed) {
    this.pageDetailed = pageDetailed;
  }

  public List<Integer> getPageSizeOption() {
    return pageSizeOption;
  }

  public void setPageSizeOptionDefault() {
    this.pageSizeOption = new ArrayList<>(Arrays.asList(10, 20, 50, 100));
    this.setActivePage(0);
    this.setActiveSize(this.pageSizeOption.get(0));
  }

}
