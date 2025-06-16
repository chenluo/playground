plugins {
    alias(libs.plugins.springboot)
    alias(libs.plugins.springboot.dependency.management)
}

group = "com.chenluo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks{
    bootJar {
        enabled = false
    }
}

dependencies {
    // https://mvnrepository.com/artifact/org.apache.httpcomponents.client5/httpclient5
    implementation("org.apache.httpcomponents.client5:httpclient5:5.5")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}