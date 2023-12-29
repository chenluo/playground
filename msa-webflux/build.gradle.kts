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
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("io.asyncer:r2dbc-mysql:1.0.4")
    testImplementation("io.projectreactor:reactor-test")
    implementation(project(":tool-logger"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}