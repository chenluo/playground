plugins {
    id("java")
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.3")
    implementation("org.elasticsearch.client:elasticsearch-rest-high-level-client:7.7.0")
    implementation(project(":tool-logger"))
}