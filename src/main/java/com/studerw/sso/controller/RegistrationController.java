package com.studerw.sso.controller;

import com.studerw.sso.user.User;
import com.studerw.sso.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private static final Logger log = Logger.getLogger(RegistrationController.class);

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String get(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String post(@ModelAttribute User user,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       final RedirectAttributes redirectAttributes) throws IOException, ClassNotFoundException {

        if (StringUtils.isBlank(user.getUsername()) ||
            StringUtils.isBlank(user.getFirstname()) ||
            StringUtils.isBlank(user.getLastname()) ){
            redirectAttributes.addFlashAttribute("errorMsg", "All Fields must be filled in");
            redirectAttributes.addFlashAttribute("error", Boolean.TRUE);
            return "redirect:/registration";
        }
        else if (this.userService.getByUserName(user.getUsername()) != null){
            redirectAttributes.addFlashAttribute("errorMsg", "All Fields must be filled in");
            redirectAttributes.addFlashAttribute("error", Boolean.TRUE);
            return "redirect:/registration";
        }
        this.userService.saveUser(user);
        redirectAttributes.addFlashAttribute("msg", "user: " + user.getUsername() + " has been created");
        return "redirect:/login";
    }
}
