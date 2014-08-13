package com.studerw.sso;

import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

/**
 * user: studerw
 * Date: 8/4/14
 */
public class SSOCookie {
    private static final Logger log = Logger.getLogger(SSOCookie.class);
    public static final String NAME = "com.studerw.SSO_COOKIE";
    public static final String BANNER = "SSO_BANNER";

    public static Cookie create(String userId, HttpServletRequest request) {
        String ssoValue = UUID.randomUUID().toString() + ":" + userId.trim();
        Cookie ssoCookie = new Cookie(NAME, ssoValue);
        log.debug("Setting session sso cookie value: " + ssoValue);
        ssoCookie.setMaxAge(-1);
        ssoCookie.setPath("/");
        String domain = request.getServerName();
        log.debug("Setting domain cookie to domain: " + domain);
        ssoCookie.setDomain(domain);
        return ssoCookie;
    }

    public static String getId(Cookie cookie) {
        String name = cookie.getName();
        if (!NAME.equals(name)) {
            throw new IllegalArgumentException("invalid SSO Cookie name: " + name);
        }
        String val = cookie.getValue();
        String[] splits = val.split(":");
        return splits[0];
    }

    public static String getUser(Cookie cookie) {
        String name = cookie.getName();
        if (!NAME.equals(name)) {
            throw new IllegalArgumentException("invalid SSO Cookie name: " + name);
        }
        String val = cookie.getValue();
        String[] splits = val.split(":");
        return splits[1];
    }

    public static boolean isBanner(Cookie cookie) {
        String name = cookie.getName();
        if (!NAME.equals(name)) {
            throw new IllegalArgumentException("invalid SSO Cookie name: " + name);
        }
        String val = cookie.getValue();
        String[] splits = val.split(":");
        return Arrays.asList(splits).contains(BANNER);
    }

    public static void setBanner(Cookie cookie, HttpServletRequest request) {
        if (isBanner(cookie)) {
            return;
        }
        String val = cookie.getValue();
        StringBuilder strBuilder = new StringBuilder(val);
        strBuilder.append(":").append(BANNER);
        cookie.setValue(strBuilder.toString());
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        String domain = request.getServerName();
        log.debug("Setting domain cookie to domain: " + domain);
        cookie.setDomain(domain);

    }

    public static void destroyCookie(Cookie cookie, HttpServletRequest request) {
        cookie.setValue("");
        String domain = request.getServerName();
        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setMaxAge(0);
    }
}
