package com.studerw.sso.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/documentation")
public class DocumentationController {
    private static final Logger log = Logger.getLogger(DocumentationController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String get(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "documentation";
    }

}
