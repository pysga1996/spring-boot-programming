package vn.vissoft.ems.core.controller;

import lombok.Data;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import vn.vissoft.ems.core.dto.LoginDTO;
import vn.vissoft.ems.core.services.AuthCommonService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@Data
@VariableResolver(DelegatingVariableResolver.class)
public class LoginVM implements Serializable {

  private static final long serialVersionUID = 1L;

  @WireVariable
  private AuthCommonService authCommonService;

  private LoginDTO user;
  private String mesg;

  @Init
  public void init() {
    user = new LoginDTO();
  }

  @NotifyChange("mesg")
  @Command //@Command annotates a command method
  public void login() {
    LoginDTO userAuth = authCommonService.login(user);
    if (userAuth != null) {
      //add cookie
      HttpServletResponse response = (HttpServletResponse) Executions.getCurrent()
          .getNativeResponse();
      Cookie userCookie = authCommonService.createOrDelCookie(userAuth.getSessionToken(), false);
      response.addCookie(userCookie);
      Executions.sendRedirect("/index"); // HOME PAGE
    } else {
      this.mesg = "Tài khoản hoặc mật khẩu không chính xác!";
    }
  }

}
