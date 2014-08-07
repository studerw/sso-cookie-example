package com.studerw.sso.controller;

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

@Controller
@RequestMapping("/index")
public class IndexController {
    private static final Logger log = Logger.getLogger(IndexController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model, HttpServletRequest request) {
        model.addAttribute("userId", request.getRemoteUser());
        model.addAttribute("message", "index");
        int numCookies = 0;
        Cookie[] myCookies = request.getCookies();
        if (myCookies != null) {
            for (Cookie cookie : myCookies) {
                log.trace("cookie: " + cookie.getName() + ":" + cookie.getValue());
            }
            numCookies = myCookies.length;

        }
        model.addAttribute("numCookies", numCookies);
        model.addAttribute("myCookies", myCookies);
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addSession(@RequestParam(value = "sessionName") String sessionName,
                             @RequestParam(value = "sessionVal") String sessionVal,
                             HttpServletRequest request,
                             final RedirectAttributes redirectAttributes) {
        if (StringUtils.isNotBlank(sessionName) && StringUtils.isNotBlank(sessionVal)) {
            log.debug("adding session: " + sessionName + " = " + sessionVal);
            WebUtils.setSessionAttribute(request, sessionName.trim(), sessionVal.trim());
            redirectAttributes.addFlashAttribute("msg", "Session Attribute Added Successfully");
        }
        return "redirect:/index";
    }


}
