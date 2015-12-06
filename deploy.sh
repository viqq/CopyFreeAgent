#!/usr/bin/env bash
#use only on prod

echo "Deploying..."

cd /Projects/FreeAgent/
git pull
mvn clean install -Dmaven.test.skip=true
if [ -d /var/lib/tomcat7/webapps/ROOT/ ]; then
   rm -r /var/lib/tomcat7/webapps/ROOT/
fi
cd /Projects/FreeAgent/webapp/target
mv ./free-agent-base-1.0-SNAPSHOT /var/lib/tomcat7/webapps/
cd /var/lib/tomcat7/webapps/
mv free-agent-base-1.0-SNAPSHOT ROOT
service tomcat7 restart

echo "Finished"