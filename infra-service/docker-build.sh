#!/bin/bash
docker build -t ${LOCAL_IMAGE} --force-rm .
if [ ! -z "${REMOTE_IMAGE}" ]
then
  docker tag ${LOCAL_IMAGE} ${REMOTE_IMAGE}
  docker push ${REMOTE_IMAGE}
fi