package com.studerw.sso;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
    private static final Logger log = Logger.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    log.debug("Session created: " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.debug("Session destroyed: " + se.getSession().getId());
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        log.debug("Session attribute added: " + event.getName() + ":" + event.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        log.debug("Session attribute removed: " + event.getName() + ":" + event.getValue());

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        log.debug("Session attribute replaced: " + event.getName() + ":" + event.getValue());

    }
}
