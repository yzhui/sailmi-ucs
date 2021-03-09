#!/bin/bash
mkdir log
java -Dserver.port=9081 -Dcsp.sentinel.dashboard.server=localhost:9080 -jar sentinel/sentinel-dashboard-1.8.1.jar > log/sentinel.log &
java -jar lib/ucs-admin.jar > log/admin.log &
java -jar lib/ucs-auth.jar > log/auth.log &
java -jar lib/ucs-desk.jar > log/desk.log &
java -jar lib/ucs-develop.jar > log/develop.log &
java -jar lib/ucs-log.jar > log/logger.log &
java -jar lib/ucs-resource.jar > log/resource.log &
java -jar lib/ucs-system.jar > log/system.log &
java -jar lib/ucs-user.jar > log/user.log &
java -jar lib/ucs-gateway.jar > log/gateway.log &
tail -f log/*.log
