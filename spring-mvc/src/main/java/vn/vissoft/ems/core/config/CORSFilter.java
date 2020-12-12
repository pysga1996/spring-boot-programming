package vn.vissoft.ems.core.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.vissoft.ems.core.services.AuthCommonService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

@Component
public class CORSFilter implements Filter {

  public static final Logger logger = LogManager.getLogger(CORSFilter.class);
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss:SSS");

  @Autowired
  private AuthCommonService authCommonService;

  /**
   * Request validation - if authorized = true => allow request - if authorized = false => redirect
   * to '/login' - if authorized = null => redirect to '/index'
   */
  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = null;
    HttpServletResponse response = null;
    if ((req instanceof HttpServletRequest)) {
      request = (HttpServletRequest) req;
    }

    if ((res instanceof HttpServletResponse)) {
      response = (HttpServletResponse) res;
    }

    Boolean authorized = authCommonService.requestValidation(request);
    if (authorized == null) { // if uri = '/ems/login' && authorized => redirect to '/index'
      response.sendRedirect(request.getContextPath() + "/index");
      return;
    } else if (!authorized) {
      logger.info("Request denied !!!"); // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.sendRedirect(request.getContextPath() + "/login");// Redirect to login page.
      return;
    }

    if (response != null) {
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Methods", "*");
      response.setHeader("Access-Control-Allow-Credentials", "true");
      response.setHeader("Access-Control-Allow-Headers", "*");
      response.setHeader("Access-Control-Max-Age", "86400");

      // fix OPTIONS request method
      if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        response.setStatus(HttpServletResponse.SC_OK);
      } else {
        doFilter(request, response, chain);
      }
    }
  }

  @Override
  public void init(FilterConfig filterConfig) {
  }

  @Override
  public void destroy() {
  }

  private void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    Date startTime = new Date();
    String startTimeStr = dateFormat.format(startTime);

    res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
    res.setHeader("Access-Control-Allow-Credentials", "true");
    chain.doFilter(req, res);

    Date endTime = new Date();
    Long duration = Long.valueOf(endTime.getTime() - startTime.getTime());

    String ipRemote = req.getRemoteAddr();
    String uri = req.getRequestURI().replace(req.getContextPath(), "");
    String logPath = req.getServerName() + ":" + req.getServerPort();
    String linkFull = req.getRequestURI();
    String logStart = MessageFormat.format("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}|{10}",
        new Object[]{"start_action", "CNC_EMS", startTimeStr, "UNKNOWN_USER", ipRemote, logPath,
            uri,
            getAllParameter(req), linkFull, duration, ""});
    logger.info(logStart);
  }

  private String getAllParameter(HttpServletRequest req) {
    StringBuilder params = new StringBuilder();
    try {
      Enumeration<String> parameterNames = req.getParameterNames();
      while (parameterNames.hasMoreElements()) {
        String paramName = parameterNames.nextElement();
        params.append(paramName).append(":");
        String[] paramValues = req.getParameterValues(paramName);
        for (String paramValue : paramValues) {
          params.append(paramValue).append(";");
        }
      }
    } catch (Exception ex) {
      logger.error("getAllParameter: ", ex.getMessage());
    }
    return params.toString();
  }
}
