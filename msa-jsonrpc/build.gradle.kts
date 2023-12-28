plugins {
    alias(versions.plugins.springboot)
    alias(versions.plugins.springboot.dependency.management)
    id("java")
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.briandilley.jsonrpc4j:jsonrpc4j:1.6")
    implementation("org.springframework.boot:spring-boot-starter-web")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}