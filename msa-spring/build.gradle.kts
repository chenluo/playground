plugins {
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
    id("java-gradle.java-application-conventions")
    id("org.flywaydb.flyway") version "9.8.1"
}

group = "com.chenluo"
version = "2022.0806.0000"
configurations.all() {
    exclude("org.springframework.boot", "spring-boot-starter-logging")
}

application {
    mainClass.set("com.chenluo.Application")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-webflux")


    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("mysql:mysql-connector-java:8.0.28")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    implementation("org.apache.kafka:kafka-clients:2.8.0")
    // https://mvnrepository.com/artifact/org.springframework.kafka/spring-kafka
    implementation("org.springframework.kafka:spring-kafka")

    implementation(project(":tool-logger"))
}


tasks.getByName<Test>("test") {
//    useJUnit() // for junit4
    useJUnitPlatform()
}

//flyway {
//    driver = "com.mysql.jdbc.Driver"
//    schemas = arrayOf("test_db2")
//    url = "jdbc:mysql://192.168.50.42:3306/test_db2"
//    user = "chen"
//    password = "HPaADeEl6K6Mo+ib"
//}