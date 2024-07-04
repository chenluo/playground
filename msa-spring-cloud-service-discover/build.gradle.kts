plugins {
    java
    alias(versions.plugins.springboot)
    alias(versions.plugins.springboot.dependency.management)
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
//	implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
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
