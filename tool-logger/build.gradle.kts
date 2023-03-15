plugins {
    id("java")
    id("java-library")
}

group = "com.chenluo"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    api("org.apache.logging.log4j:log4j-api:2.20.0")
    api("org.apache.logging.log4j:log4j-core:2.20.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}