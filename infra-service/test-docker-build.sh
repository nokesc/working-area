#!/bin/bash
export LOCAL_IMAGE=sb1:master-latest
export REMOTE_IMAGE=sdg-poc1.osii.com:8085/${LOCAL_IMAGE}
./docker-build.sh