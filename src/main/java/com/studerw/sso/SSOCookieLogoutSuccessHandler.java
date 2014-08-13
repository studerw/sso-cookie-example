package com.studerw.sso;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security {@Code LogoutSuccessHandler} that takes care of the SSO Domain Cookie.
 * <p>
 * Unfortunately, we cannot use the {@code <logout delete-cookies .../>} namespace config
 * to deal with domain cookies.
 * user: studerw
 * Date: 8/10/14
 */
public class SSOCookieLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    private static final Logger log = Logger.getLogger(SSOCookieLogoutSuccessHandler.class);

    public SSOCookieLogoutSuccessHandler(String defaultTargetURL) {
        this.setDefaultTargetUrl(defaultTargetURL);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("logging out of the app");
        Cookie ssoCookie = WebUtils.getCookie(request, SSOCookie.NAME);
        if (ssoCookie != null){
            SSOCookie.destroyCookie(ssoCookie, request);
            response.addCookie(ssoCookie);
        }
        super.onLogoutSuccess(request, response, authentication);

    }
}
