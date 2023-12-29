plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.elasticsearch.client:elasticsearch-rest-high-level-client:7.7.0")
    implementation(project(":tool-logger"))
}
