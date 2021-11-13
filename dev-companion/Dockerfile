FROM adoptopenjdk/openjdk11:x86_64-ubi-minimal-jdk-11.0.11_9-slim


##### User setup
ARG user=devops
ARG group=devops
ARG uid=1000
ARG gid=1000
RUN  microdnf update \
        && microdnf install -y --nodocs \
                shadow-utils \
                hostname \
        && groupadd -g ${gid} ${group} \
        && useradd -u ${uid} -g ${group} -m ${user}
USER ${user}
RUN mkdir -p /home/${user}/src/target
WORKDIR /home/${user}/src

ENTRYPOINT [ "bash", "-l" ]