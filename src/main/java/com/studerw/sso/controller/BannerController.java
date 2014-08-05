package com.studerw.sso.controller;

import com.studerw.sso.SSOCookie;
import com.studerw.sso.filter.BannerFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/banner")
public class BannerController {
    private static final Logger log = Logger.getLogger(BannerController.class);
    public static final String INDEX_PATH = "/index";

    @RequestMapping(method = RequestMethod.GET)
    public String get(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "banner";
    }

    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestParam(value = "accept", required = false) String accept,
                     HttpServletRequest request,
                     HttpServletResponse response) throws IOException {
        if (StringUtils.isBlank(accept)) {
            String redirect = request.getContextPath() + BannerFilter.BANNER_PATH;
            response.sendRedirect(redirect);
        } else {
            Cookie ssoCookie = WebUtils.getCookie(request, SSOCookie.NAME);
            SSOCookie.setBanner(ssoCookie, request);
            response.addCookie(ssoCookie);
            String redirect = request.getContextPath() + INDEX_PATH;
            response.sendRedirect(redirect);
        }
    }


}
