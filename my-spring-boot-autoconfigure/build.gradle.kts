plugins {
    id("java-gradle.java-library-conventions")
}

group = "com.chenluo.spring"
version = "0.0.1"

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    implementation("org.springframework.boot:spring-boot:2.4.10")
    implementation("org.springframework.boot:spring-boot-autoconfigure:2.4.10")
}