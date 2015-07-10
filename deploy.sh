#!/usr/bin/env bash
#use only on prod

echo "Deploying..."

cd /Projects/FreeAgent/
git pull
mvn clean install -Dmaven.test.skip=true
rm -r /var/lib/tomcat7/webapps/free-agent-base-1.0-SNAPSHOT/
cd /Projects/FreeAgent/webbapp/target/
mv ./free-agent-base-1.0-SNAPSHOT /var/lib/tomcat7/webapps/
service tomcat7 restart

echo "Finished"