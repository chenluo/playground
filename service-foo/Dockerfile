FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY ./build/libs/*-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","-Dserver.port=8080", "app.jar"]