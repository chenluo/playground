plugins {
    id("java")
    id("application")
    id("org.springframework.boot").version("3.2.1")
    id("io.spring.dependency-management" ).version("1.0.11.RELEASE")
}

group = "com.chenluo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application.mainClass = "com.chenluo.Write"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(":db"))
    implementation("org.flywaydb:flyway-core:10.17.0")
    implementation("org.flywaydb:flyway-database-postgresql:10.17.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.springframework.kafka:spring-kafka")

}

tasks.test {
    useJUnitPlatform()
}