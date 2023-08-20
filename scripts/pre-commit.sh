#!/bin/bash

echo "Running formatting checks..."

./gradlew spotlessCheck >/dev/null

status=$?

[ $status -ne 0 ] && ./gradlew spotlessApply && echo -e "ERROR: Spotless check failed, and an apply has been ran" && exit 1
exit 0