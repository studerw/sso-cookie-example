package com.studerw.sso;

import com.studerw.sso.user.User;
import com.studerw.sso.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashMap;

/**
 * user: studerw
 * Date: 8/10/14
 */
public class SSOAuthenticationProvider implements AuthenticationProvider {
    private static final Logger log = Logger.getLogger(SSOAuthenticationProvider.class);

    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("authenticating: " + authentication);
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = (String) authentication.getPrincipal();
        User user;
        try {
            user = this.userService.getByUserName(username);
            if (user == null) {
                throw new UsernameNotFoundException(username + " is not a known username");
            }
        }
        catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
        return new BaseAuthentication(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public static class BaseAuthentication implements Authentication {
        User user;

        public BaseAuthentication(User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return AuthorityUtils.createAuthorityList("user");
        }

        @Override
        public Object getCredentials() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getDetails() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getPrincipal() {
            return this.user;
        }

        @Override
        public boolean isAuthenticated() {
            return this.user != null;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            throw new UnsupportedOperationException("can't set this property");
        }

        @Override
        public String getName() {
            return this.user.getFirstname() + " " + this.user.getLastname();
        }
    }
}
