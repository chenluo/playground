plugins {
    id("java")
    kotlin("jvm") version "1.7.10"
}

group = "org.example"
version = "2202.09.19.2044"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation(kotlin("stdlib-jdk8"))
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.8.0")
    testImplementation("io.mockk:mockk:1.12.8")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.21")


}

tasks.getByName<Test>("test") {
    useJUnit() // for junit4
//    useJUnitPlatform()
}