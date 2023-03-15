plugins {
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
    id("java-gradle.java-application-conventions")
    id("war")
    id("org.flywaydb.flyway") version "9.8.1"
}

group = "com.chenluo"
version = "2022.0806.0000"
configurations.all() {
    exclude("org.springframework.boot", "spring-boot-starter-logging")
}

application {
    mainClass.set("com.chenluo.Application")
}

dependencies {
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-webflux")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("io.sentry:sentry:6.4.1")
    implementation("io.sentry:sentry-log4j2:6.4.1")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.28")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    implementation("com.zaxxer:HikariCP:4.0.3")
//    implementation("com.ctrip.framework.apollo:apollo-client:1.7.0")

    implementation(project(":tool-logger"))
}

//flyway {
//    driver = "com.mysql.jdbc.Driver"
//    schemas = arrayOf("test_db2")
//    url = "jdbc:mysql://192.168.50.42:3306/test_db2"
//    user = "chen"
//    password = "HPaADeEl6K6Mo+ib"
//}