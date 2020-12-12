package vn.vissoft.ems.core.controller;

import org.springframework.util.StringUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Tabbox;
import vn.vissoft.ems.core.dto.MenuDTO;
import vn.vissoft.ems.core.dto.NavigationDTO;
import vn.vissoft.ems.core.model.TabInfo;
import vn.vissoft.ems.core.services.AuthCommonService;
import vn.vissoft.ems.core.services.MenusService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static vn.vissoft.ems.core.controller.MainVM.NAVIGATION;
import static vn.vissoft.ems.core.controller.MainVM.TABBOXES;

@VariableResolver(DelegatingVariableResolver.class)
public class SidebarVM {

  @WireVariable
  private MenusService menusService;
  @WireVariable
  private AuthCommonService authCommonService;

  private NavigationDTO navigationModel;
  private String userName = "Admin";
  private List<MenuDTO> menuList;
  private boolean collapsed = false; //sidebar is collapsed for narrow screen

  protected ListModelList<TabInfo> tabInfos;
  protected TabInfo selected;

  @Init
  public void init(@ScopeParam(NAVIGATION) NavigationDTO navModel,
      @ScopeParam(TABBOXES) ListModelList<TabInfo> tabInfosModel) {
    navigationModel = navModel;
    menuList = menusService.findAllByIsActive(true);
    tabInfos = tabInfosModel;
  }

  @Command
  public void navigate(@BindingParam("menu") MenuDTO menu, @BindingParam("tbox") Tabbox tbox) {
    String targetPath = menu.getPath();
    if (!StringUtils.isEmpty(targetPath) && !targetPath.equals(NavigationDTO.BLANK_ZUL)) {
      navigationModel.setContentUrl(targetPath);
      BindUtils.postNotifyChange(null, null, navigationModel, "contentUrl");
    }

    if (menu != null && tabInfos != null && menu.getPath() != null && !"".equals(menu.getPath())) {
      TabInfo curentTab = new TabInfo(menu.getMenuId(), menu.getLabelKey(), menu.getPath(),
          menu.getIcon());
      boolean hasThisTab = false;
      for (int i = 0; i < tabInfos.size(); ++i) {
        TabInfo itemTabInfos = tabInfos.get(i);
        if (itemTabInfos.getId() != null && itemTabInfos.getId().equals(menu.getMenuId())) {
          //da mo tab nay
          hasThisTab = true;
          itemTabInfos.setSelected(true);
          tabInfos.get(i).setSelected(true);
          tabInfos.get(i).setIconsclass(menu.getIcon());
          tabInfos.get(i).setId(menu.getMenuId());
          tbox.setSelectedIndex(i);
        } else {
          tabInfos.get(i).setSelected(false);
        }
      }
      curentTab.setSelected(true);
      if (!hasThisTab) {
        curentTab.setIndexTab(tabInfos.size());
        tabInfos.add(curentTab);
      }
//            saved tab id
      HttpSession session = (HttpSession) (Executions.getCurrent()).getDesktop().getSession()
          .getNativeSession();
      session.setAttribute("previousTabID", session.getAttribute("currentTabID"));
      session.setAttribute("currentTabID", menu.getMenuId());
    } else {
      Clients
          .showNotification(Labels.getLabel("app.common.error"), "error", null, "after_end", 8000,
              true);
    }
    Sessions.getCurrent().setAttribute("tabInfos", tabInfos);
  }

  /**
   * Logout
   */
  @Command
  public void logout() {
    // add black list
    HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
    authCommonService.logout(request);
    // set token auth null && redirect to '/login'
    HttpServletResponse response = (HttpServletResponse) Executions.getCurrent()
        .getNativeResponse();
    Cookie userCookie = authCommonService.createOrDelCookie("", true);
    response.addCookie(userCookie);
    Executions.sendRedirect("/login");
  }

  // medium breakpoint 768 + 190 (sidebar width)
  @MatchMedia("all and (min-width: 958px)")
  @NotifyChange("collapsed")
  public void beWide() {
    collapsed = false;
  }

  @MatchMedia("all and (max-width: 957px)")
  @NotifyChange("collapsed")
  public void beNarrow() {
    collapsed = true;
  }

  public String getUserName() {
    return userName;
  }

  public List<MenuDTO> getMenuList() {
    return menuList;
  }

  public boolean isCollapsed() {
    return collapsed;
  }

  public void setCollapsed(boolean collapsed) {
    this.collapsed = collapsed;
  }

  public NavigationDTO getNavigationModel() {
    return navigationModel;
  }
}
