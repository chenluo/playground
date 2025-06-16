plugins {
    id("application")
    alias(libs.plugins.springboot)
    alias(libs.plugins.springboot.dependency.management)
}

group = "com.chenluo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application.mainClass = "com.chenluo.Read"

dependencies {
    implementation(project(":base"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(":db"))
    implementation("org.flywaydb:flyway-core:10.17.0")
    implementation("org.flywaydb:flyway-database-postgresql:10.17.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}