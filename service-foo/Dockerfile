FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAVA_ARGS
ENV JAVA_OPTS="${JAVA_ARGS} -Xmx=1G -Xms=1G"
ENV SERVER_PORT 0
EXPOSE ${SERVER_PORT}
COPY ./build/libs/*-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]