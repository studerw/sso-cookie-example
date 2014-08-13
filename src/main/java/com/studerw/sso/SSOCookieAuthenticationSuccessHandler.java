package com.studerw.sso;

import com.studerw.sso.user.User;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;

/**
 * User: studerw
 * Date: 8/12/14
 */
public class SSOCookieAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final Logger log = Logger.getLogger(SSOCookieAuthenticationSuccessHandler.class);
    public static final String BANNER_PATH = "/banner";

    @Override
    public void onAuthenticationSuccess(javax.servlet.http.HttpServletRequest request,
                                        javax.servlet.http.HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        Cookie ssoCookie = WebUtils.getCookie(request, SSOCookie.NAME);
        User user = (User)authentication.getPrincipal();
        if (ssoCookie == null){
            log.debug("no sso cookie - adding to response");
            ssoCookie = SSOCookie.create(user.getUsername(), request);
            WebUtils.setSessionAttribute(request, SSOCookie.NAME, SSOCookie.getId(ssoCookie));
            response.addCookie(ssoCookie);
        }

        super.onAuthenticationSuccess(request, response, authentication);

    }
}
