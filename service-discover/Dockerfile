#FROM eclipse-temurin:17-jdk-alpine
FROM eclipse-temurin:17
VOLUME /tmp
ARG JAVA_ARGS
ENV JAVA_OPTS="${JAVA_ARGS} -Xmx=512M -Xms=512M"
ENV SERVER_PORT 8761
EXPOSE ${SERVER_PORT}
COPY ./build/libs/*-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
