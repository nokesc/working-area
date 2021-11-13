FROM openjdk:11-oracle
VOLUME /tmp

WORKDIR /app

COPY jvm-args jvm-args

ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib lib
COPY ${DEPENDENCY}/META-INF classes/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes classes

EXPOSE 8080/tcp

ENV JDK_JAVA_OPTIONS=@jvm-args/memory

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-cp","classes:lib/*","com.cnokes.example.sb1.SB1AppConfiguration"]