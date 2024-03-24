plugins {
    alias(versions.plugins.springboot)
    alias(versions.plugins.springboot.dependency.management)
    id("java")
    id("application")
    id("java-library")
    alias(versions.plugins.flyway)
}


application {
    mainClass.set("com.chenluo.Application")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-cache")
//    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.github.ben-manes.caffeine:caffeine")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    runtimeOnly("mysql:mysql-connector-java:8.0.28")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    // https://mvnrepository.com/artifact/org.flywaydb/flyway-database-postgresql
//    runtimeOnly("org.flywaydb:flyway-database-postgresql")

    implementation("org.apache.kafka:kafka-clients:3.1.0")
    // https://mvnrepository.com/artifact/org.springframework.kafka/spring-kafka
    implementation("org.springframework.kafka:spring-kafka")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.12.0")
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.5.1")

    // https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-sqs
    implementation("com.amazonaws:aws-java-sdk-sqs:1.12.504")
    testImplementation("org.testcontainers:mysql:1.18.3")
    testImplementation("org.testcontainers:postgresql:1.18.3")
    testImplementation("org.testcontainers:kafka:1.18.3")
    testImplementation("org.testcontainers:testcontainers:1.18.3")
    testImplementation("org.testcontainers:junit-jupiter:1.18.3")

    implementation(project(":msa-base"))
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