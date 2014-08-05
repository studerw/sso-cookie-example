package com.studerw.sso.filter;

import com.studerw.sso.SSOCookie;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
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
public class DomainCookieFilter extends BaseFilter {
    private static final Logger log = Logger.getLogger(DomainCookieFilter.class);
    private FilterConfig filterConfig = null;
    public static final String LOGIN_PATH = "/login";
    public static final String AJAX_HEADER = "X-REQUESTED-WITH";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("DomainCookieFilter init...");
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

    @Override
    public void afterIgnore(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        Cookie ssoCookie = checkSSOCookie(httpServletRequest);
        if (ssoCookie == null) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            if (isAjax(httpServletRequest)) {
                httpServletResponse.sendError(419, "SSOCookie Missing - Ajax Request");
                return;
            }
            else {
                String redirect = httpServletRequest.getContextPath() + LOGIN_PATH;
                log.info("No SSOCookie - redirecting to: " + redirect);
                httpServletResponse.sendRedirect(redirect);
                return;
            }
        }
        HttpServletRequest remoteUserReq = new RemoteUserRequestWrapper(httpServletRequest);
        chain.doFilter(remoteUserReq, response);
    }

    /**
     * @param request
     * @return the global Cookie IFF it exists AND the session reference matches. Otherwise,
     *         the session is cleared and null is returned.
     */
    protected Cookie checkSSOCookie(HttpServletRequest request) {
        Cookie ssoCookie = WebUtils.getCookie(request, SSOCookie.NAME);
        if (ssoCookie == null) {
            request.getSession().invalidate();
        }

        //if the session SSO val doesn't match the current SSO cookie, it means we've been logged out and logged back in somewhere else
        //so we now need to clear our own session
        else {
            String ssoSessionVal = (String) WebUtils.getSessionAttribute(request, SSOCookie.NAME);
            String ssoCookieVal = SSOCookie.getId(ssoCookie);
            if (!ObjectUtils.equals(ssoSessionVal, ssoCookieVal)) {
                log.info("Current Session does not match SSO Cookie - invalidating app session");
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
