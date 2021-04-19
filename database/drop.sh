#!/usr/bin/env bash
cd `dirname $0`

echo "**********************************************************************"
echo "*"
echo "* Database drop"
echo "*"
echo "**********************************************************************"

APPLICATION_YML=../src/main/resources/application.yml,../src/main/resources/application-variable.yml
java -jar ../db-migration-1.0.0.jar -mode dropall --spring.config.location=file:${APPLICATION_YML}
