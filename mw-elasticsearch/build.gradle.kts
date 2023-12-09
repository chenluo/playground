plugins {
    id("java")
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
    implementation("org.elasticsearch.client:elasticsearch-rest-high-level-client:7.7.0")
    implementation(project(":tool-logger"))
}