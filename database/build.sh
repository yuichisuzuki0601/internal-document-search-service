#!/usr/bin/env bash
cd `dirname $0`

echo "**********************************************************************"
echo "*"
echo "* Database build"
echo "*"
echo "**********************************************************************"

APPLICATION_YML=../src/main/resources/application.yml,../src/main/resources/application-variable.yml
java -jar ysd-db-migration-1.0.0.jar -mode rebuild -rootdir ./ --spring.config.location=file:${APPLICATION_YML}
