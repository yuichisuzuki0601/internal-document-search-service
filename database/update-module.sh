#!/usr/bin/env bash
cd `dirname $0`

MODULE_PATH1=../../ysd-util

cd ${MODULE_PATH1}

./mvnw clean install

cd -

MODULE_PATH2=../../ysd-db-migration

cd ${MODULE_PATH2}

./mvnw clean package

cd -

rm -f ysd-db-migration-*.jar

cp ${MODULE_PATH2}/target/ysd-db-migration-*.jar ./