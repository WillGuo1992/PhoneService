#!/bin/bash

export PHONE_NAME="PhoneService"
export PHONE_HOME=$(cd `dirname $0`; pwd)

PID_FILE=$PHONE_HOME/$PHONE_NAME.pid

if [ -f "$PID_FILE" ]
then
	PID=`cat $PID_FILE`
	kill $PID
	if [ $? == 0 ]
	then
		echo "killed PID: "$PID
		rm $PID_FILE -f
	else
		echo "kill failed! PID: "$PID
	fi
else
	echo "PID_FILE not exist: "$PID_FILE
fi
