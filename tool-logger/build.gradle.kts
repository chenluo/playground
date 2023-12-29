plugins {
    id("java")
    id("java-library")
}


repositories {
    mavenCentral()
}

dependencies {
    api("org.apache.logging.log4j:log4j-api:2.20.0")
    api("org.apache.logging.log4j:log4j-core:2.20.0")
    api("org.apache.logging.log4j:log4j-slf4j2-impl:2.20.0")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}