package com.studerw.sso.filter;

import com.studerw.sso.SSOAuthenticationProvider;
import com.studerw.sso.SSOCookie;
import com.studerw.sso.user.User;
import com.studerw.sso.user.UserService;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * user: studerw
 * Date: 7/26/14
 */
public class DomainCookieFilter extends GenericFilterBean {
    private static final Logger log = Logger.getLogger(DomainCookieFilter.class);
    private FilterConfig filterConfig = null;
    public static final String LOGIN_PATH = "/login";
    public static final String AJAX_HEADER = "X-REQUESTED-WITH";

    @Autowired
    UserService userService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Cookie ssoCookie = WebUtils.getCookie(httpServletRequest, SSOCookie.NAME);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("sso cookie: " + ssoCookie);
        log.debug("authentication: " + authentication);

        if (authentication == null && ssoCookie == null && isAjax(httpServletRequest)){
            httpServletResponse.sendError(419, "SSOCookie Missing - Ajax Request");
            return;
        }
        if (authentication != null && ssoCookie == null) {
            SecurityContextHolder.clearContext();
            if (isAjax(httpServletRequest)) {
                httpServletResponse.sendError(419, "SSOCookie Missing - Ajax Request");
                return;
            }
        } else if (ssoCookie != null && authentication != null && authentication.isAuthenticated()) {
            String ssoSessionVal = (String) WebUtils.getSessionAttribute(httpServletRequest, SSOCookie.NAME);
            String ssoCookieVal = SSOCookie.getId(ssoCookie);

            if (!ObjectUtils.equals(ssoSessionVal, ssoCookieVal)) {
                log.info("Current Session does not match SSO Cookie - invalidating app session");
                SecurityContextHolder.clearContext();
                httpServletRequest.getSession().invalidate();
                WebUtils.setSessionAttribute(httpServletRequest, SSOCookie.NAME, ssoCookieVal);
            }

        } else if (ssoCookie != null && (authentication == null || !authentication.isAuthenticated())) {
            String username = SSOCookie.getUser(ssoCookie);
            try {
                User user = this.userService.getByUserName(username);
                if (user == null) {
                    if (isAjax(httpServletRequest)) {
                        httpServletResponse.sendError(419, "SSOCookie Missing - Ajax Request");
                        return;
                    } else {
                        String redirect = httpServletRequest.getContextPath() + "/j_spring_security_logout";
                        httpServletResponse.sendRedirect(redirect);
                        return;
                    }
                }
                SSOAuthenticationProvider.BaseAuthentication auth = new SSOAuthenticationProvider.BaseAuthentication(user);
                SecurityContextHolder.getContext().setAuthentication(auth);
                WebUtils.setSessionAttribute(httpServletRequest, SSOCookie.NAME, SSOCookie.getId(ssoCookie));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }


//        Cookie ssoCookie = checkSSOCookie(httpServletRequest);
//        if (ssoCookie == null) {
//            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//            if (isAjax(httpServletRequest)) {
//                httpServletResponse.sendError(419, "SSOCookie Missing - Ajax Request");
//                return;
//            }
//        }
        chain.doFilter(request, response);
    }


    /**
     * @param request
     * @return the global Cookie IFF it exists AND the session reference matches. Otherwise,
     *         the session is cleared and null is returned.
     */
    protected Cookie checkSSOCookie(HttpServletRequest request) {
        Cookie ssoCookie = WebUtils.getCookie(request, SSOCookie.NAME);
        if (ssoCookie == null) {
            SecurityContextHolder.clearContext();
            request.getSession().invalidate();
        }

        //if the session SSO val doesn't match the current SSO cookie, it means we've been logged out and logged back in somewhere else
        //so we now need to clear our own session
        else {
            String ssoSessionVal = (String) WebUtils.getSessionAttribute(request, SSOCookie.NAME);
            String ssoCookieVal = SSOCookie.getId(ssoCookie);
            if (!ObjectUtils.equals(ssoSessionVal, ssoCookieVal)) {
                log.info("Current Session does not match SSO Cookie - invalidating app session");
                SecurityContextHolder.clearContext();
                request.getSession().invalidate();
                WebUtils.setSessionAttribute(request, SSOCookie.NAME, ssoCookieVal);
            }
        }
        return ssoCookie;
    }

    /**
     * Look for the header: 'X-Requested-With: XMLHttpRequest'
     *
     * @param request
     * @return true if ajax header is contained in request
     */
    protected boolean isAjax(HttpServletRequest request) {
        String xRequestHdr = request.getHeader(AJAX_HEADER);
        return StringUtils.equalsIgnoreCase("XMLHttpRequest", xRequestHdr);
    }
}
