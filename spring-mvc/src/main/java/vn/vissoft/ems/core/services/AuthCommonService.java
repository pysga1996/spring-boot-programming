package vn.vissoft.ems.core.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.vissoft.ems.core.config.i18n.Translator;
import vn.vissoft.ems.core.config.jwt.CustomUserDetails;
import vn.vissoft.ems.core.config.jwt.JwtTokenProvider;
import vn.vissoft.ems.core.config.jwt.UserService;
import vn.vissoft.ems.core.dto.LoginDTO;
import vn.vissoft.ems.core.helper.AppConst;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Service
public class AuthCommonService {

  private static final Logger logger = LogManager.getLogger(AuthCommonService.class);

  @Autowired
  private Environment env;
  @Autowired
  private UsersService customerService;
  // REDIS_FOR_AUTHOZ
//  @Autowired
//  private RedisTemplate template;
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  private JwtTokenProvider tokenProvider;
  @Autowired
  private UserService customUserDetailsService;

  @Value("${auth.token.ttl}")
  private Integer JWT_EXPIRATION;

  /**
   * create or delete cookie
   *
   * @param token, isDelete
   * @return String jwt
   */
  public Cookie createOrDelCookie(String token, boolean isDelete) {
    if (token != null) {
      Cookie userCookie = new Cookie(AppConst.AUTH.AUTH_KEY, token);
      if (isDelete) {
        userCookie.setMaxAge(0); // delete
      } else {
        userCookie.setMaxAge(JWT_EXPIRATION); // create
      }
      return userCookie;
    }
    return null;
  }

  /**
   * @param request
   * @return String jwt
   */
  public String getTokenFromCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie coo : cookies) {
        if (AppConst.AUTH.AUTH_KEY.equals(coo.getName())) {
          return coo.getValue();
        }
      }
    }
    return "";
  }

  /**
   * get user info from jwt in store
   */
  public LoginDTO getCustomerByToken(String token) {
    Long userId = tokenProvider.getUserIdFromJWT(token);
    if (userId != null) {
      LoginDTO user = customerService.findByUserId(userId);
      if (user != null) {
        user.setSessionToken(token);
      }
      return user;
    }
    return null;
  }

  /**
   * verify jwt in store
   */
  public boolean sessionValidation(String token, HttpServletRequest req) {
    if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
      // Lấy id user từ chuỗi jwt
      Long userId = tokenProvider.getUserIdFromJWT(token);
      // Lấy thông tin người dùng từ id
      UserDetails userDetails = customUserDetailsService.loadUserById(userId);
      if (userDetails != null) {
        // Nếu người dùng hợp lệ, set thông tin cho Seturity Context
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
      }
    }

    return false;
  }

  /**
   * @param request
   * @return - true if: authorized = true && uri != '/login' || authorized = false && uri = '/login'
   * - null if: uri = '/login' && authorized = true - else false
   */
  public Boolean requestValidation(HttpServletRequest request) {
    if (request == null) {
      return false;
    }
    if (env.getProperty("dev.mode").equals("dev")) {
      return true;
    }

    String uri = request.getRequestURI();
    if (!StringUtils.isEmpty(uri)) {
      boolean isLoginUri = "/ems/login".equals(uri);

      List<String> allowList = new ArrayList<>();
      allowList.add("/favicon"); // favicon
      allowList.add("/ems/zkau"); // allow zk language, ...
      allowList.add("/ems/webjars"); // allow bootstrap
      if (allowList.stream().anyMatch(s -> uri.contains(s))) {
        return true;
      }
      String auth = getTokenFromCookie(request);
      if (StringUtils.isEmpty(auth)) {
        if (isLoginUri) {
          return true; // allow with uri login
        }
        // ghi log fail
        logger.info("requestValidation: Authorization not defined (" + uri + ")");
        Enumeration headerNames = request.getHeaderNames();
        JSONObject json = new JSONObject();
        try {
          while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            json.put(key, value);
          }
          System.out.printf("JSON: %s", json.toString(2));
        } catch (Exception ex) {

        }
        return false;
      }
      // BackList token
      // REDIS_FOR_AUTHOZ
//      if (template.opsForValue().get(auth) != null) {
//        if (isLoginUri) return true; // allow with uri login
//        return false;
//      }

      // kiem tra token hop le (ton tai trong store)
      boolean authorized = sessionValidation(auth, request);
      if (isLoginUri) {
        if (authorized) {
          return null; // if authorized => redirect from '/login' to '/index'
        } else {
          return true; // allow with uri login
        }
      }

      return authorized;
    }
    return false;
  }

  /**
   * - verify user - create jwt => store
   *
   * @return jwt
   */
  public LoginDTO login(LoginDTO user) {
    try {
      // verify username and password.
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              user.getUserName(),
              user.getPassword()
          )
      );
      // Nếu không xảy ra exception tức là thông tin hợp lệ
      SecurityContextHolder.getContext()
          .setAuthentication(authentication); // Set thông tin authentication vào Security Context

      // Trả về jwt cho người dùng.
      LoginDTO customer = new LoginDTO();
      customer.setSessionToken(
          tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal()));
      return customer;
    } catch (Exception ex) {
      logger.info(Translator.toLocale("login.invalid"));
      return null;
    }
  }

  /**
   * add jwt to blacklist
   */
  public void logout(HttpServletRequest request) {
    if (request != null) {
      // REDIS_FOR_AUTHOZ
//      template.opsForValue().set(getTokenFromCookie(request), getTokenFromCookie(request), JWT_EXPIRATION);
    }
  }

  /**
   * - verify jwt token - create jwt => store
   *
   * @param request
   * @return loginDTO
   */
  public LoginDTO getUserInfo(HttpServletRequest request) {
    LoginDTO userDTO = getCustomerByToken(getTokenFromCookie(request));
    userDTO.setPassword(null); // SET PASSWORD NULL
    return userDTO;
  }
}
