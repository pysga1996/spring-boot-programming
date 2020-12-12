package vn.vissoft.ems.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.vissoft.ems.core.services.AttributesService;

@Controller
@RequestMapping("/")
public class HomeController {

  @Autowired
  AttributesService attributesService;

  @GetMapping("login")
  public String getLoginPage() {
    return "login/index";
  }

  @GetMapping("index")
  public String getHomePage() {
    return "index";
  }

  @GetMapping(value = "favicon", produces = MediaType.IMAGE_JPEG_VALUE)
  public @ResponseBody
  byte[] getFavicon() {
    return attributesService.getFavicon();
  }
}
