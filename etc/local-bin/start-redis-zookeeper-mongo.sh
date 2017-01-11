#!/bin/bash
echo 'start'
sudo  /opt/zookeeper/bin/zkServer.sh start &
echo 'redis'
sudo redis-server /opt/redis/redis.conf &
echo 'started'
