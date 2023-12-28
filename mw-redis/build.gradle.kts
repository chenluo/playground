plugins {
    id("java")
}


repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":tool-logger"))
    implementation("org.redisson:redisson:3.20.0")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}