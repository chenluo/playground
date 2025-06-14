import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.springboot)
    alias(libs.plugins.springboot.dependency.management)
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // https://mvnrepository.com/artifact/com.vladmihalcea/hibernate-types-52
//    implementation("com.vladmihalcea:hibernate-types-52:2.21.1")
    implementation("org.flywaydb:flyway-core")
    // https://mvnrepository.com/artifact/org.flywaydb/flyway-database-postgresql
//    runtimeOnly("org.flywaydb:flyway-database-postgresql")
    implementation(project(":msa-base"))
    implementation(project(":tool-logger"))
}

//tasks.withType<KotlinCompile> {
//    kotlinOptions {
//        freeCompilerArgs += "-Xjsr305=strict"
//        jvmTarget = "21"
//    }
//}

tasks.withType<Test> {
    useJUnitPlatform()
}
