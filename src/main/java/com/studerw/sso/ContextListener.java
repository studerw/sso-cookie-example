package com.studerw.sso;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * user: studerw
 * Date: 8/7/14
 */
public class ContextListener implements ApplicationListener<ApplicationEvent> {
    private static final Logger log = Logger.getLogger(ContextListener.class);
    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ContextRefreshedEvent){
            ApplicationContext appContext = ((ContextRefreshedEvent) event).getApplicationContext();
            ApplicationContext parent = appContext.getParent();
            String parentName = parent == null ? "none" : parent.getApplicationName();
            log.debug("**********************App Context " + appContext.getDisplayName() + " from parent " + parent + " ***********************************");
            log.info("AppContext " + appContext.getApplicationName() + " : beans(" + appContext.getBeanDefinitionCount() + ")");
            for (String bean : appContext.getBeanDefinitionNames()){
                log.debug("bean: " + bean);
            }
        }
    }
}
