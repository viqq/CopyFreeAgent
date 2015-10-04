#!/usr/bin/env bash
#use only on prod

rm -r /var/lib/tomcat7/webapps/ROOT/css/
rm -r /var/lib/tomcat7/webapps/ROOT/js/
rm -r /var/lib/tomcat7/webapps/ROOT/fonts/
rm -r /var/lib/tomcat7/webapps/ROOT/WEB-INF/pages/

cp -r /Projects/FreeAgent/webapp/src/main/webapp/css /var/lib/tomcat7/webapps/ROOT/css/
cp -r /Projects/FreeAgent/webapp/src/main/webapp/js /var/lib/tomcat7/webapps/ROOT/js/
cp -r /Projects/FreeAgent/webapp/src/main/webapp/fonts /var/lib/tomcat7/webapps/ROOT/fonts/
cp -r /Projects/FreeAgent/webapp/src/main/webapp/WEB-INF/pages/ /var/lib/tomcat7/ROOT/WEB-INF/pages/

echo "Static resourses were updated"