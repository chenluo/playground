plugins {
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
    id("java-gradle.java-application-conventions")
    id("org.flywaydb.flyway") version "9.8.1"
}

group = "com.chenluo"
version = "2022.0806.0000"
configurations.all() {}

application {
    mainClass.set("com.chenluo.Application")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")
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

    // https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-sqs
    implementation("com.amazonaws:aws-java-sdk-sqs:1.12.504")
    testImplementation("org.testcontainers:mysql:1.18.3")
    testImplementation("org.testcontainers:kafka:1.18.3")
    testImplementation("org.testcontainers:testcontainers:1.18.3")
    testImplementation("org.testcontainers:junit-jupiter:1.18.3")

    implementation(project(":msa-base"))


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