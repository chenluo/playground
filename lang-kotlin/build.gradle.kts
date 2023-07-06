plugins {
    id("java")
    kotlin("jvm") version "1.7.10"
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "org.example"
version = "2202.09.19.2044"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation(kotlin("stdlib-jdk8"))
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation("io.mockk:mockk:1.12.8")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.21")
    implementation("org.springframework.boot:spring-boot-starter")


}

tasks.getByName<Test>("test") {
//    useJUnit() // for junit4
    useJUnitPlatform()
}