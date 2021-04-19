#!/usr/bin/env bash
cd `dirname $0`

curl -XGET "http://localhost:9200/article-*/_search?pretty"
