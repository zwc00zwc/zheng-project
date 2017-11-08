#!/bin/bash
# pid=`ps -ef|grep "org.elasticsearch.bootstrap.Elasticsearch"|gawk '$0 !~/grep/ {print $2}' |tr -s '\n' ' '`
# pid=`ps -ef|grep "org.elasticsearch.bootstrap.Elasticsearch"`
#grep -v grep 去除带有grep的数据
#awk '{print$2}' 打印第二个参数
pid=`ps -ef|grep "zheng-main"| grep -v grep | awk '{print$2}'`
kill $pid
echo $pid
