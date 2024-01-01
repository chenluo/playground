import java.time.Instant

plugins {
    id("java")
    kotlin("jvm") version "1.9.20"
    id("com.google.protobuf") version "0.9.1"
    kotlin("plugin.spring") version "1.9.20"
    kotlin("plugin.jpa") version "1.9.20"
}
allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
    group = "com.chenluo"
    version = Instant.now().toEpochMilli()
}

subprojects {
    apply(plugin = "java")
    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
    }
    configurations.all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
    }
}
