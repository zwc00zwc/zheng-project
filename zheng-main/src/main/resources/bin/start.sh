#!/bin/bash
#java虚拟机启动参数
JAVA_OPTS="-ms1024m -mx1024m -Xmn512m -Djava.awt.headless=true -XX:MaxPermSize=256m"

#加载java空间
#-Djava.ext.dirs=../lib:$JAVA_HOME/jre/lib/ext

nohup java $JAVA_OPTS -Djava.ext.dirs=../lib:$JAVA_HOME/jre/lib/ext zheng.ApplicationMain >/dev/null 2>&1 &