plugins {
    alias(libs.plugins.springboot)
    alias(libs.plugins.springboot.dependency.management)
    id("java")
    id("application")
    alias(libs.plugins.flyway)
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