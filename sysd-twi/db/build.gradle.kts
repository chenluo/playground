plugins {
    id("java")
    id("org.springframework.boot").version("3.2.1")
    id("io.spring.dependency-management" ).version("1.0.11.RELEASE")
    id("java-library")
}

group = "com.chenluo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks{
    bootJar {
        enabled = false
    }
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.postgresql:postgresql")
    api("org.flywaydb:flyway-core:10.17.0")
    api("org.flywaydb:flyway-database-postgresql:10.17.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    api("redis.clients:jedis:5.0.2")
}

tasks.test {
    useJUnitPlatform()
}