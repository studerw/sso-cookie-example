package com.studerw.sso.controller;

import com.studerw.sso.SSOCookie;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static final Logger log = Logger.getLogger(LoginController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String get(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public String post(@RequestParam(value = "j_username") String jUsername,
//                       HttpServletRequest request,
//                       HttpServletResponse response,
//                       final RedirectAttributes redirectAttributes) {
//
//        //if the SSO cookie exists, we probably didn't get sent by the filter, so just redirect to index
//        if (WebUtils.getCookie(request, SSOCookie.NAME) != null) {
//            return "redirect:/index";
//        }
//        if (StringUtils.isBlank(jUsername)){
//            redirectAttributes.addFlashAttribute("errorMsg", "Invalid user");
//            redirectAttributes.addFlashAttribute("error", Boolean.TRUE);
//            return "redirect:/login";
//        }
//        Cookie ssoCookie = SSOCookie.create(jUsername, request);
//        //Create the Cookie object
//        WebUtils.setSessionAttribute(request, SSOCookie.NAME, SSOCookie.getId(ssoCookie));
//        response.addCookie(ssoCookie);
//        return "redirect:/index";
//    }
}
