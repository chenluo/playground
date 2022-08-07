plugins {
    id("java-gradle.java-application-conventions")
}

group = "org.example"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.3")
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation(project(":my-spring-boot-starter"))
    implementation("org.elasticsearch.client:elasticsearch-rest-high-level-client:7.7.0")
}