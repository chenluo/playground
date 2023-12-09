plugins {
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
    id("application")
    id("java-library")
    id("org.flywaydb.flyway") version "9.8.1"
    id("org.graalvm.buildtools.native") version "0.9.17"
}

group = "com.chenluo"
version = "2023.0507.0000"

application {
    mainClass.set("com.chenluo.SpringNativeApp")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("mysql:mysql-connector-java:8.0.28")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
}