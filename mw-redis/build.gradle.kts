plugins {
    id("java")
}

group = "com.chenluo"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation(project(":tool-logger"))
    implementation("org.redisson:redisson:3.20.0")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}