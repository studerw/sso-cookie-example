package com.studerw.sso.filter;

import com.studerw.sso.SSOCookie;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
public class BannerFilter extends GenericFilterBean {
    private static final Logger log = Logger.getLogger(BannerFilter.class);
    private FilterConfig filterConfig = null;
    public final static String BANNER_ORIGINAL_URL = "SSO_BANNER_ORIGINAL_URL";
    public static final String SESSION_BANNER = "SSO_BANNER";
    public static final String BANNER_PATH = "/banner";
    public static final String[] IGNORES = {BANNER_PATH};


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Cookie ssoCookie = WebUtils.getCookie(httpServletRequest, SSOCookie.NAME);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("sso cookie: " + ssoCookie);
        log.debug("authentication: " + authentication);
//        if (ssoCookie == null || authentication == null || !authentication.isAuthenticated()) {
//            throw new RuntimeException("We should never have gotten here - sso cookie must be set and user must be authenticated");
//        }

//        //were going to the banner itself, pass through
//        if (isIgnored(httpServletRequest, IGNORES)) {
//            chain.doFilter(request, response);
//            return;
//        }

        log.debug("checking banner...");
        if (!acceptedBanner(httpServletRequest)) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            String redirect = httpServletRequest.getContextPath() + BANNER_PATH;
            log.info("No Banner - redirecting to: " + redirect);
            saveOriginalURL(httpServletRequest);
            httpServletResponse.sendRedirect(redirect);
            return;
        }
        String original = (String) WebUtils.getSessionAttribute(httpServletRequest, BANNER_ORIGINAL_URL);
        if (StringUtils.isNotBlank(original)) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            log.info("Original URL: " + original + " exists - redirecting");
            httpServletRequest.getSession().removeAttribute(BANNER_ORIGINAL_URL);
            httpServletResponse.sendRedirect(original);
            return;
        }
        chain.doFilter(request, response);
        return;
    }


    protected boolean isIgnored(HttpServletRequest request, String[] ignores) {
        String current = request.getServletPath();
        for (String ignore : ignores) {
            if (current.matches(ignore)) {
                return true;
            }
        }
        return false;
    }

    protected boolean acceptedBanner(HttpServletRequest request) {
        Cookie ssoCookie = WebUtils.getCookie(request, SSOCookie.NAME);
        if (ssoCookie == null) {
            return false;
        }
        return SSOCookie.isBanner(ssoCookie);
    }

    protected void saveOriginalURL(HttpServletRequest request) {
        StringBuilder original = new StringBuilder(request.getRequestURL());
        if (StringUtils.isNotEmpty(request.getQueryString())) {
            original.append('?').append(request.getQueryString());
        }
        WebUtils.setSessionAttribute(request, BANNER_ORIGINAL_URL, original.toString());
    }
}
