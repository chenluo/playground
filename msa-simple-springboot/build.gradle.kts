plugins {
    java
    alias(versions.plugins.springboot)
    alias(versions.plugins.springboot.dependency.management)
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(project(":tool-logger"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
