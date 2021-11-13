#!/bin/bash -e

usage() {
    >&2 echo "Usage: ${0} <command>
    Commands:
        build
        run
        remote-build

    project-context is required for sourcing project configuration.  Expected variables:
        LOCAL_IMAGE:  the locally built image name

        One of:
            REMOTE_IMAGE:  the remotely built image name
            REMOTE REPO:  used to create the REMOTE_IMAGE value by pre-pending to LOCAL_IMAGE    
"
}

if [[ ! -f "project-context" ]]; then
    >&2 echo "project-context is missing"
    usage; exit 1
fi

. ./project-context

required() {
    if [[ -z "${2}" ]]; then
        >&2 echo "${1} is a required variable"
        usage; exit 1
    fi
}

build() {
    required "LOCAL_IMAGE" "${LOCAL_IMAGE}"
    >&2 echo "LOCAL_IMAGE=${LOCAL_IMAGE}"
    docker build -t ${LOCAL_IMAGE} --force-rm .
}

remote-build() {
    required "REMOTE_IMAGE" "${REMOTE_IMAGE}"
    >&2 echo "REMOTE_IMAGE=${REMOTE_IMAGE}"
    docker tag "${LOCAL_IMAGE}" "${REMOTE_IMAGE}"
    docker push "${REMOTE_IMAGE}"
}

run() {
    required "LOCAL_IMAGE" "${LOCAL_IMAGE}"
    docker run -it --rm --entrypoint /bin/bash ${LOCAL_IMAGE}
}

case $1 in
    run)
        run    
    ;;
    build)
        build
    ;;
    remote-build)
        if [[ -z "${REMOTE_IMAGE}" ]]; then
            if [[ -z "${REMOTE_REPO}" ]]; then
                >&2 echo "REMOTE_IMAGE or REMOTE_REPO are required when calling remote-build"
                exit -1
            else
                REMOTE_IMAGE=${REMOTE_REPO}/${LOCAL_IMAGE}
            fi
        fi
        build
        remote-build
    ;;
    *)
        >&2 echo "Unknown command: \"$1\""
        usage; exit 1
    ;;
esac