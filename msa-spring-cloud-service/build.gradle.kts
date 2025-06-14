plugins {
    java
    alias(libs.plugins.springboot)
    alias(libs.plugins.springboot.dependency.management)
}


repositories {
    mavenCentral()
    maven {
        url =
            uri("https://artifactory-oss.prod.netflix.net/artifactory/maven-oss-candidates")
    }
}

extra["springCloudVersion"] = "2023.0.0"

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom(
            "org.springframework.cloud:spring-cloud-dependencies:${
                property(
                    "springCloudVersion"
                )
            }"
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
