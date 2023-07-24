plugins {
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
    id("java-gradle.java-application-conventions")
}

group = "com.chenluo"
version = "unspecified"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.chenluo.BatchApp")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.28")
    implementation(project(":msa-base"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}