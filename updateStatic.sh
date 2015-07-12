#!/usr/bin/env bash
#use only on prod

cp -r /Projects/FreeAgent/webapp/src/main/webapp/css /var/lib/tomcat7/webapps/free-agent-base-1.0-SNAPSHOT/css/
cp -r /Projects/FreeAgent/webapp/src/main/webapp/ls /var/lib/tomcat7/webapps/free-agent-base-1.0-SNAPSHOT/js/
cp -r /Projects/FreeAgent/webapp/src/main/webapp/fonts /var/lib/tomcat7/webapps/free-agent-base-1.0-SNAPSHOT/fonts/
cp -r /Projects/FreeAgent/webapp/src/main/webapp/WEB-INF/pages/ /var/lib/tomcat7/webapps/free-agent-base-1.0-SNAPSHOT/WEB-INF/pages/

echo "Static resourses were updated"