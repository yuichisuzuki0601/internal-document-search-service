#!/usr/bin/env bash
cd `dirname $0`

curl -XDELETE "http://localhost:9200/article-*?pretty"