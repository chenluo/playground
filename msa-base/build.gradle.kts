plugins {
    alias(versions.plugins.springboot)
    alias(versions.plugins.springboot.dependency.management)
    id("java")
    id("application")
    alias(versions.plugins.flyway)
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
}

tasks.bootJar {
    enabled = false
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}