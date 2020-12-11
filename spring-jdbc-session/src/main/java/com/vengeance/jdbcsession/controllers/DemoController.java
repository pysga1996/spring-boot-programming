package com.vengeance.jdbcsession.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemoController {

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        List<String> favoriteColors = getFavColors(session);
        model.addAttribute("favoriteColors", favoriteColors);
        model.addAttribute("sessionId", session.getId());
        return "index";
    }

    @GetMapping("/saveColor")
    public String saveMessage
        (@RequestParam("color") String color,
            HttpServletRequest request) {

        List<String> favoriteColors
            = getFavColors(request.getSession());
        if (!StringUtils.isEmpty(color)) {
            favoriteColors.add(color);
            request.getSession().
                setAttribute("favoriteColors", favoriteColors);
        }
        return "redirect:/";
    }

    @SuppressWarnings("unchecked")
    private List<String> getFavColors(HttpSession session) {
        List<String> favoriteColors = (List<String>) session.getAttribute("favoriteColors");

        if (favoriteColors == null) {
            favoriteColors = new ArrayList<>();
        }
        return favoriteColors;
    }
}
