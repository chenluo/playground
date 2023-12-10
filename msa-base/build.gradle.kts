plugins {
    alias(versions.plugins.springboot)
    alias(versions.plugins.springboot.dependency.management)
    id("java")
    id("application")
    id("java-library")
    alias(versions.plugins.flyway)
}

group = "com.chenluo"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.springframework.boot:spring-boot-starter")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}