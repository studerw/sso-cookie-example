package com.studerw.sso.filter;

import com.studerw.sso.SSOCookie;
import org.apache.log4j.Logger;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: studerw
 * Date: 7/26/14
 */
public class BannerFilter extends BaseFilter {
    private static final Logger log = Logger.getLogger(BannerFilter.class);
    private FilterConfig filterConfig = null;

    public static final String SESSION_BANNER = "CHROME_BANNER";
    public static final String BANNER_PATH = "/banner";
    public static final String[] IGNORES = { BANNER_PATH };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("BannerFilter init...");
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

    @Override
    public void afterIgnore(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (!isIgnored(httpServletRequest, IGNORES) && !acceptedBanner(httpServletRequest)) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            String redirect = httpServletRequest.getContextPath() + BANNER_PATH;
            log.info("No Banner - redirecting to: " + redirect);
            httpServletResponse.sendRedirect(redirect);
            return;
        }
        else {
            chain.doFilter(request, response);
        }

    }

    protected boolean acceptedBanner(HttpServletRequest request) {
        Cookie ssoCookie = WebUtils.getCookie(request, SSOCookie.NAME);
        return SSOCookie.isBanner(ssoCookie);
    }

}
