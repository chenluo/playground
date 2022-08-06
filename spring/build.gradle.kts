plugins {
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
    id("java-gradle.java-application-conventions")
}

version = "2022.0806.0000"

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot:spring-boot-starter-logging")
    }
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.springframework.boot:spring-boot-starter-logging")
    }
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.springframework.boot:spring-boot-starter-logging")
    }
//    implementation group:"com.chenluo.spring", name:"my-spring-boot-starter", version:"0.0.1"
//    implementation group:"com.chenluo.spring", name:"my-spring-boot-autoconfigure", version:"0.0.1"
    implementation(project(":my-spring-boot-starter"))
}