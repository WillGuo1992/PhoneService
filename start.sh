#!/bin/bash


JAVA_OPTS="-XX:MaxPermSize=256m -XX:PermSize=64m -Xms1024m -Xmx1024m -Xss512k -XX:+AggressiveOpts -XX:+UseBiasedLocking -XX:+DisableExplicitGC -XX:MaxTenuringThreshold=31 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly"

NAME="PhoneService" 
export PHONE_NAME="PhoneService"
export PHONE_HOME=$(cd `dirname $0`; pwd)

PID_FILE=$PHONE_HOME/$PHONE_NAME.pid

CLASS_PATH=$PHONE_HOME/bin

LIBS=$PHONE_HOME/libs

PHONE_MAIN_CLASS="cn.com.navia.PhoneService.App"

#java
#JAVA_BIN=$(dirname `which java`)
#JAVA_EXEC=$JAVA_BIN/java
#JAVA_HOME=$(cd $JAVA_BIN/..; pwd)
JAVA_HOME=/usr/share/jdk1.7.0_45
JAVA_EXEC=$JAVA_HOME/bin/java


echo -e "PHONE_HOME:\t $PHONE_HOME"
echo -e "JAVA_HOME:\t $JAVA_HOME"
echo -e "JAVA_EXEC:\t $JAVA_EXEC"
echo -e "JAVA_OPTS:\t $JAVA_OPTS"
echo -e "MAIN_CLASS:\t $PHONE_MAIN_CLASS" 

for jar in `find $LIBS -type f`
do
	CLASS_PATH=$CLASS_PATH:$jar
done 

EXEC_CMD="$JAVA_EXEC $JAVA_OPTS -classpath $CLASS_PATH $PHONE_MAIN_CLASS"

rm $PID_FILE -f
$EXEC_CMD &
echo $! > $PID_FILE
