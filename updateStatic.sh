#!/usr/bin/env bash
#use only on prod

rm -r /var/lib/tomcat7/webapps/free-agent-base-1.0-SNAPSHOT/css/
rm -r /var/lib/tomcat7/webapps/free-agent-base-1.0-SNAPSHOT/js/
rm -r /var/lib/tomcat7/webapps/free-agent-base-1.0-SNAPSHOT/fonts/
rm -r /var/lib/tomcat7/webapps/free-agent-base-1.0-SNAPSHOT/WEB-INF/pages/

cp -r /Projects/FreeAgent/webapp/src/main/webapp/css /var/lib/tomcat7/webapps/free-agent-base-1.0-SNAPSHOT/css/
cp -r /Projects/FreeAgent/webapp/src/main/webapp/js /var/lib/tomcat7/webapps/free-agent-base-1.0-SNAPSHOT/js/
cp -r /Projects/FreeAgent/webapp/src/main/webapp/fonts /var/lib/tomcat7/webapps/free-agent-base-1.0-SNAPSHOT/fonts/
cp -r /Projects/FreeAgent/webapp/src/main/webapp/WEB-INF/pages/ /var/lib/tomcat7/webapps/free-agent-base-1.0-SNAPSHOT/WEB-INF/pages/

echo "Static resourses were updated"