#!/bin/bash

#echo ${CRAC_FILES_DIR}
#CRAC_FILES_DIR=`eval echo ${CRAC_FILES_DIR}`
mkdir -p $CRAC_FILES_DIR

if [ -z "$(ls -A $CRAC_FILES_DIR)" ]; then
  echo "starting fresh"
  echo 128 > /proc/sys/kernel/ns_last_pid; java -XX:CRaCCheckpointTo=$CRAC_FILES_DIR -jar /opt/app/data-redis-1.0.0-SNAPSHOT.jar&
  java -XX:CRaCCheckpointTo=${CRAC_FILES_DIR} -jar /opt/app/data-redis-1.0.0-SNAPSHOT.jar&
  sleep 30
  jcmd /opt/app/data-redis-1.0.0-SNAPSHOT.jar JDK.checkpoint
  sleep 30
else
  echo "starting from checkpoint"
  java -XX:CRaCRestoreFrom=${CRAC_FILES_DIR} -jar /opt/app/data-redis-1.0.0-SNAPSHOT.jar&
  sleep infinity
fi
