package com.studerw.sso.filter;

import com.studerw.sso.SSOCookie;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Overrides the {@code HttpServletRequest.getRemoteUser()} method using the
 * user id stored in the SSO cookie. Format of cookie value is <uuid:userId>
 */
public class RemoteUserRequestWrapper extends HttpServletRequestWrapper {

    public RemoteUserRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getRemoteUser() {
        Cookie cookie = WebUtils.getCookie((HttpServletRequest) this.getRequest(), SSOCookie.NAME);
        if (cookie == null) {
            throw new IllegalStateException("The SSO Cookie was not found");
        }
        return SSOCookie.getUser(cookie);
    }
}
