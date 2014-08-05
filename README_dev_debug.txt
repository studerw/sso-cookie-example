To Debug the web app using Maven, Jetty OR Tomcat7, and Eclipse:

From Command Line:
    Linux / Cygwin:
    export MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n -XX:MaxPermSize=512m -Xms1024m -Xmx2048m"

    Windows:
    set MAVEN_OPTS=-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n -XX:MaxPermSize=512m -Xms1024m -Xmx2048m

    mvn jetty:run OR mvn tomcat7:run

    At the very beginning of the Tomcat/Jetty output, you should see the following line:
    'Listening for transport dt_socket at address: 4000'

Then in Eclipse:

    Run -> Debug Configurations -> Remote Java Application -> Create new by double click
        Name: remote debug
        Project: <project>
        Connection: Standard socket attach
        Host: localhost
        port: 4000
        allow termination of remote: checked

    Hit Apply and close or debug to start (the next time you won't need to recreate the configuration - just go to Run -> Debug Configurations -> Remote Java App -> tcop-cloud remote debug

    Add Breakpoints in any code as you would in non-webapp or JUnit test.

**** If you get a error trying to connect to socket when running mvn jetty:run or mvn tomcat7:run from command line above, you probably have an old Java Process bound to the port 4000. Kill it with process manager.

Memory Errors in Tomcat7:
Windows: set MAVEN_OPTS=-XX:MaxPermSize=1024m
Linux / Cygwin: export MAVEN_OPTS="-XX:MaxPermSize=1024m"
