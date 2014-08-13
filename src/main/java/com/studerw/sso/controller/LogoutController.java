package com.studerw.sso.controller;

import com.studerw.sso.SSOCookie;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    private static final Logger log = Logger.getLogger(LogoutController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String get(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
//        log.info("logging out of the app");
//        Cookie ssoCookie = WebUtils.getCookie(request, SSOCookie.NAME);
//        if (ssoCookie != null){
//            SSOCookie.destroyCookie(ssoCookie, request);
//            response.addCookie(ssoCookie);
//        }
//        request.getSession().invalidate();
        return "logout";
    }

}

