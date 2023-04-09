plugins {
    id("java")
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "com.chenluo"

repositories {
    mavenCentral()
}

configurations.all() {
    exclude("org.springframework.boot", "spring-boot-starter-logging")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation(project(":tool-logger"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}