FROM openjdk:17-oracle
LABEL authors="chenluo"
COPY build/libs/read-1.0-SNAPSHOT.jar ./app.jar
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT sh -c "java ${JAVA_OPTIONS} -jar app.jar"
