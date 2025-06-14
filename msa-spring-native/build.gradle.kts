plugins {
    alias(libs.plugins.springboot)
    alias(libs.plugins.springboot.dependency.management)
    alias(libs.plugins.flyway)
    id("java")
    id("application")
    id("java-library")
    id("org.graalvm.buildtools.native") version "0.9.17"
}


application {
    mainClass.set("com.chenluo.SpringNativeApp")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("mysql:mysql-connector-java:8.0.28")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
}