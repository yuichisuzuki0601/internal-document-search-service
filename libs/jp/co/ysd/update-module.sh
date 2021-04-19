#!/usr/bin/env bash
cd `dirname $0`

VERSION1=1.0.0
MODULE_PATH1=../../../../../ysd-util

cd ${MODULE_PATH1}

./mvnw clean install

cd -

mkdir -p ./ysd-util/${VERSION1}

rm -f ./ysd-util/${VERSION1}/ysd-util-*.jar

cp ${MODULE_PATH1}/target/ysd-util-*.jar ./ysd-util/${VERSION1}


VERSION2=1.0.0
MODULE_PATH2=../../../../../ysd-dao

cd ${MODULE_PATH2}

./mvnw clean install

cd -

mkdir -p ./ysd-dao/${VERSION2}

rm -f ./ysd-dao/${VERSION2}/ysd-dao-*.jar

cp ${MODULE_PATH2}/target/ysd-dao-*.jar ./ysd-dao/${VERSION2}