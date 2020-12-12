package vn.vissoft.ems.core.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.ComposerExt;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Tabbox;

public class PlusTabComposer implements Composer<Tabbox>, ComposerExt<Tabbox> {

  private ListModelList tabsModel;
  private int counter = 0;

  @Override
  public void doBeforeComposeChildren(Tabbox tabbox) throws Exception {
    tabsModel = new ListModelList();
    tabsModel.add("Tab A");
    tabsModel.add("Tab B");
    tabsModel.add("Tab C");
    tabbox.setAttribute("tabsModel", tabsModel);
  }

  @Override
  public void doAfterCompose(Tabbox tabbox) throws Exception {
    tabbox.addEventListener("onTabAdd", event -> {
      counter++;
      tabsModel.add("New Tab " + counter);
      tabbox.setSelectedIndex(tabsModel.size() - 1);
    });
  }

  public ListModelList getTabsModel() {
    return tabsModel;
  }

  @Override
  public ComponentInfo doBeforeCompose(Page page, Component component, ComponentInfo componentInfo)
      throws Exception {
    return componentInfo;
  }

  @Override
  public boolean doCatch(Throwable throwable) throws Exception {
    return false;
  }

  @Override
  public void doFinally() throws Exception {
  }
}
