package vn.vissoft.ems.core.controller;

import org.springframework.util.StringUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import vn.vissoft.ems.core.dto.MenuDTO;
import vn.vissoft.ems.core.dto.NavigationDTO;
import vn.vissoft.ems.core.services.AuthCommonService;
import vn.vissoft.ems.core.services.MenusService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static vn.vissoft.ems.core.controller.MainVM.NAVIGATION;

@VariableResolver(DelegatingVariableResolver.class)
public class TabboxVM {

  @WireVariable
  private MenusService menusService;
  @WireVariable
  private AuthCommonService authCommonService;

  private NavigationDTO navigationModel;
  private String userName = "Admin";
  private List<MenuDTO> menuList;
  private boolean collapsed = false; //sidebar is collapsed for narrow screen

  @Init
  public void init(@ScopeParam(NAVIGATION) NavigationDTO navModel) {
    navigationModel = navModel;
    menuList = menusService.findAllByIsActive(true);
  }

  @Command
  public void navigate(@BindingParam("menu") MenuDTO menu) {
    String targetPath = menu.getPath();
    if (!StringUtils.isEmpty(targetPath) && !targetPath.equals(NavigationDTO.BLANK_ZUL)) {
      navigationModel.setContentUrl(targetPath);
      BindUtils.postNotifyChange(null, null, navigationModel, "contentUrl");
    }
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
