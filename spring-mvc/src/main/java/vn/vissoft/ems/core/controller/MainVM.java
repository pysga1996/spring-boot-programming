package vn.vissoft.ems.core.controller;

import lombok.Data;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.*;
import org.zkoss.zul.theme.Themes;
import vn.vissoft.ems.core.dto.NavigationDTO;
import vn.vissoft.ems.core.helper.AppConst;
import vn.vissoft.ems.core.model.TabInfo;
import vn.vissoft.ems.core.services.AttributesService;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@Data
@VariableResolver(DelegatingVariableResolver.class)
public class MainVM {

  @WireVariable
  private AttributesService attributesService;

  @Wire("#tbox")
  public Tabbox tbox;

  public static final String NAVIGATION = "navigation";
  public static final String TABBOXES = "tabboxes";
  private NavigationDTO navigationModel;

  protected ListModelList<TabInfo> tabInfos;
  protected TabInfo selected;

  public final static String ACTIVE_THEME = "active.theme";
  public final static String ACTIVE_LANG = "active.theme";
  private String langSelected = AppConst.STRING.DFLANG;
  private String themeSelected = AppConst.STRING.DFTHEME;

  @Init
  public void init(@ContextParam(ContextType.DESKTOP) Desktop desktop) {
    initNav(desktop);
    initTabs(desktop);
    initLang();
    initTheme();
    initTitle();
  }

  private void initLang() {
    String lang = (String) Sessions.getCurrent().getAttribute(ACTIVE_LANG);
    if (lang != null) {
      langSelected = lang;
    } else {
      langSelected = AppConst.STRING.DFLANG;
    }
  }

  private void initTheme() {
    String theme = (String) Sessions.getCurrent().getAttribute(ACTIVE_THEME);
    if (theme != null) {
      themeSelected = theme;
    } else {
      themeSelected = AppConst.STRING.DFTHEME;
    }
  }

  private void initTitle() {
    Executions.getCurrent().getDesktop().getFirstPage().setTitle(
        attributesService.getAppTitle()
    );
  }

  private void initNav(Desktop desktop) {
    navigationModel = new NavigationDTO();
    desktop.setAttribute(NAVIGATION, navigationModel);
  }

  private void initTabs(Desktop desktop) {
    tabInfos = new ListModelList<>();
    desktop.setAttribute(TABBOXES, tabInfos);
  }

  @Command
  public void changeTheme(@BindingParam("type") String type) {
    themeSelected = type;
    refeshTheme();
    Executions.sendRedirect(AppConst.STRING.EMPTY);
  }

  private void refeshTheme() {
    Themes.setTheme(Executions.getCurrent(), themeSelected);
    Sessions.getCurrent().setAttribute(ACTIVE_THEME, themeSelected);
  }

  @Command
  public void changeLang(@BindingParam("type") String type) {
    langSelected = type;
    refreshLang();
    Executions.sendRedirect(AppConst.STRING.EMPTY);
  }

  private void refreshLang() {
    Locale prefer_locale = langSelected.length() > 2 ?
        new Locale(langSelected.substring(0, 2), langSelected.substring(3))
        : new Locale(langSelected);
    Sessions.getCurrent().setAttribute(org.zkoss.web.Attributes.PREFERRED_LOCALE, prefer_locale);
    Sessions.getCurrent().setAttribute(ACTIVE_LANG, langSelected);
  }

  @Command
  public void updateTabID(@BindingParam("id") Long id) {
    HttpSession session = (HttpSession) (Executions.getCurrent()).getDesktop().getSession()
        .getNativeSession();
    session.setAttribute("previousTabID", session.getAttribute("currentTabID"));
    session.setAttribute("currentTabID", id);
  }

  @GlobalCommand
  @Command("closeTab")
  public void closeTab(@BindingParam("idTab") Long idTab) {
    try {
      for (int i = 0; i < tabInfos.size(); i++) {
        if (tabInfos.get(i).getId().equals(idTab)) {
          tabInfos.remove(i);
          break;
        }
      }
      HttpSession session = (HttpSession) (Executions.getCurrent()).getDesktop().getSession()
          .getNativeSession();
      Long id = (Long) session.getAttribute("previousTabID");
      for (int i = 0; i < tabInfos.size(); i++) {
        if (tabInfos.get(i).getId().equals(id)) {
          tbox.setSelectedIndex(i);
          break;
        }
      }

    } catch (Exception e) {
      for (int i = 0; i < tabInfos.size(); i++) {
        if (tabInfos.get(i).getId().equals(idTab)) {
          tabInfos.remove(i);
        }
      }
    }
  }

  @GlobalCommand
  @Command("closeAllTabs")
  public void closeAllTabs() {
    while (tabInfos.size() > 0) {
      tabInfos.remove(0);
    }
    System.out.print(tabInfos.size());
  }

  @GlobalCommand
  @Command("closeOtherTabs")
  public void closeOtherTabs(@BindingParam("idTab") Long idTab) {
    TabInfo selected = null;
    for (int i = 0; i < tabInfos.size(); i++) {
      if (tabInfos.get(i).getId().equals(idTab)) {
        selected = tabInfos.get(i);
        break;
      }
    }
    while (tabInfos.size() > 0) {
      tabInfos.remove(0);
    }
    tabInfos.add(selected);
  }

  private void showNotify(String msg) {
    Clients.showNotification(msg, "info", null, null, 1000);
  }

  @Command
  public void drop(@ContextParam(ContextType.TRIGGER_EVENT) DropEvent event) {
    Tab dragged = (Tab) event.getDragged();
    Tab target = (Tab) event.getTarget();
    if (dragged.getIndex() > target.getIndex()) {
      Tabpanel drag = dragged.getLinkedPanel();
      Tabpanel drop = target.getLinkedPanel();
      Tabpanels panels = (Tabpanels) drag.getParent();
      panels.getChildren().remove(drag);
      panels.getChildren().add(panels.getChildren().indexOf(drop), drag);
      dragged.getParent().insertBefore(dragged, target);
      dragged.setSelected(true);
      panels.invalidate();// strange behavior when I don't do this
    } else {
      Tabpanel drag = dragged.getLinkedPanel();
      Tabpanel drop = target.getLinkedPanel();
      Tabpanels panels = (Tabpanels) drag.getParent();
      panels.getChildren().remove(drag);
      panels.getChildren().add(panels.getChildren().indexOf(drop) + 1, drag);
      Tabs tabs = (Tabs) dragged.getParent();
      tabs.getChildren().remove(dragged);
      tabs.getChildren().add(tabs.getChildren().indexOf(target) + 1, dragged);
      dragged.setSelected(true);
      panels.invalidate(); // strange behavior when I don't do this
    }
  }

  public String getContentUrl() {
    return navigationModel.getContentUrl();
  }

  public NavigationDTO getNavigationModel() {
    return navigationModel;
  }
}
