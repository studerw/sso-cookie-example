sso-cookie-example
==================

Java web app demonstrating Single Sign Capability using a domain cookie.

To run a single version:

mvn tomcat7:run

go to: http://localhost:9090/sso

Login in as any user - the login step would be customized based on what is needed in your own production environment.

----

The idea is as follows:

Two applications: 'blue' and 'red'. Deploy both, either on the same host or behind a proxy.
Login to either one of the applications, and now you are logged into the other(s). Logout of anyone, and you will be logged out of the others.

To deploy this scenario, follow these instructions:

1. Run maven clean install. Copy the target/sso.war to your Tomcat/JBoss/Servlet environment as blue.war.
2. Switch the initialization parameters 'appName' and 'appName1' - the new app is now 'red', and the other app 'blue'
3. rebuild using mvn clean install. This time, copy the sso.war to your servlet container as red.war.
4. Login to each one of the apps at http://<host>/blue or http://<host>/red.
5. Login and accept the terms.
6. Now click the link to the other app. You won't have to login, accept ther terms. Your username is already set. Notice that they share a global domain cookie, but have separate sessions.
7. Logout of either one of the apps. Go back to the other app. Ajax calls will detect the logout from the other app. Clicking any other link will redirect to login.

