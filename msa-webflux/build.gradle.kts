plugins {
    id("java")
    alias(versions.plugins.springboot)
    alias(versions.plugins.springboot.dependency.management)
}


repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}