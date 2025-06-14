plugins {
    java
    alias(libs.plugins.springboot)
    alias(libs.plugins.springboot.dependency.management)
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(project(":tool-logger"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
