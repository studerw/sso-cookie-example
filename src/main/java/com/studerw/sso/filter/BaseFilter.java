package com.studerw.sso.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * user: studerw
 * Date: 8/3/14
 */
public abstract class BaseFilter implements Filter {

    public final String[] baseIgnores = { "^/resources/.*", "^/login", "/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (isIgnored(httpServletRequest, baseIgnores)){
            chain.doFilter(request, response);
        }
        else {
            afterIgnore(request, response, chain);
        }
    }

    abstract public void afterIgnore(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException;

    public boolean isIgnored(HttpServletRequest request, String[] ignores) {
        String current = request.getServletPath();
        for (String ignore : ignores) {
            if(current.matches(ignore)) {
                return true;
            }
        }
        return false;
    }

}
