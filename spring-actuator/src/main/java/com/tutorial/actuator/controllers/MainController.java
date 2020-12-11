package com.tutorial.actuator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author thanhvt
 * @project spring-actuator-demo
 */
@Controller
public class MainController {

    @GetMapping("/")
    public String home(ModelMap modelMap) {
        String rootPath = "http://localhost:8081/actuator";
        modelMap.addAttribute("actuator", rootPath);
        modelMap.addAttribute("auditevents", rootPath + "/auditevents");
        modelMap.addAttribute("autoconfig", rootPath + "/autoconfig");
        modelMap.addAttribute("beans", rootPath + "/beans");
        modelMap.addAttribute("configprops", rootPath + "/configprops");
        modelMap.addAttribute("heapdump", rootPath + "/heapdump");
        modelMap.addAttribute("threaddump", rootPath + "/threaddump");
        modelMap.addAttribute("env", rootPath + "/env");
        modelMap.addAttribute("flyway", rootPath + "/flyway");
        modelMap.addAttribute("conditions", rootPath + "/conditions");
        modelMap.addAttribute("caches", rootPath + "/caches");
        modelMap.addAttribute("health", rootPath + "/health");
        modelMap.addAttribute("info", rootPath + "/info");
        modelMap.addAttribute("loggers", rootPath + "/loggers");
        modelMap.addAttribute("liquibase", rootPath + "/liquibase");
        modelMap.addAttribute("metrics", rootPath + "/metrics");
        modelMap.addAttribute("mappings", rootPath + "/mappings");
        modelMap.addAttribute("shutdown", rootPath + "/shutdown");
        modelMap.addAttribute("trace", rootPath + "/trace");
        return "home";
    }
}
