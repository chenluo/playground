plugins {
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
    id("java-gradle.java-application-conventions")
    id("war")
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
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
//    implementation("org.apache.logging.log4j:log4j-spring-boot:2.19.0")
//    implementation("org.slf4j:slf4j-api:1.7.30")
//    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
//    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
//    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.1")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    implementation group:"com.chenluo.spring", name:"my-spring-boot-starter", version:"0.0.1"
//    implementation group:"com.chenluo.spring", name:"my-spring-boot-autoconfigure", version:"0.0.1"
//    implementation(project(":my-spring-boot-starter"))
    implementation("io.sentry:sentry:6.4.1")
    implementation("io.sentry:sentry-log4j2:6.4.1")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.28")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    implementation("com.zaxxer:HikariCP:4.0.3")
    implementation("com.ctrip.framework.apollo:apollo-client:1.7.0")
}

flyway {
    driver = "com.mysql.jdbc.Driver"
    schemas = arrayOf("test_db2")
    url = "jdbc:mysql://192.168.50.42:3306/test_db2"
    user = "chen"
    password = "HPaADeEl6K6Mo+ib"

}