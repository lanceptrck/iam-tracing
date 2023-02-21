#!/usr/bin/env bash

mvn clean package -DskipTests=true
docker build --tag=itdevops3/iam-tracing:ss-0.0.6.RELEASE .;
docker push itdevops3/iam-tracing:ss-0.0.6.RELEASE

$SHELL
