plugins {
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")

    id("java-gradle.java-application-conventions")
}

group = "com.example.sub"
version = "0.0.1-SNAPSHOT"
repositories {
    mavenCentral()
}

//jar {
//    zip64=true
//    manifest {
//        attributes "Main-Class": "$mainClassName"
//    }
//    from {
//        configurations.compile.collect { it.isDirectory() ? it : zipTree(it) }
//    }
//}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot:spring-boot-starter-logging")
    }
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.springframework.boot:spring-boot-starter-logging")
    }
    // https://mvnrepository.com/artifact/org.openjdk.jol/jol-core
    implementation("org.openjdk.jol:jol-core:0.16")
    implementation("org.apache.zookeeper:zookeeper:3.6.3")
    implementation("org.springframework.boot:spring-boot-starter-actuator") {
        exclude("org.springframework.boot:spring-boot-starter-logging")
    }
    // testImplementation("org.springframework.boot:spring-boot-aop")
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    // https://mvnrepository.com/artifact/org.apache.curator/curator-framework
    implementation("org.apache.curator:curator-framework:5.1.0")
    // https://mvnrepository.com/artifact/org.apache.curator/curator-recipes
    implementation("org.apache.curator:curator-recipes:5.1.0")

    implementation("org.springframework.boot:spring-boot-starter-aop")
    // https://mvnrepository.com/artifact/org.apache.tomcat/tomcat-jdbc
    implementation("org.apache.tomcat:tomcat-jdbc:9.0.46")
    // https://mvnrepository.com/artifact/mysql/mysql-connector-java
    implementation("mysql:mysql-connector-java:8.0.25")
    // https://mvnrepository.com/artifact/org.springframework/spring-tx
    implementation("org.springframework:spring-tx")
    implementation("org.springframework:spring-jdbc")
    // https://mvnrepository.com/artifact/org.openjdk.jol/jol-core
    compileOnly("org.openjdk.jol:jol-core:0.16")
    // https://mvnrepository.com/artifact/org.openjdk.jol/jol-benchmarks
    implementation("org.openjdk.jol:jol-benchmarks:0.16")
    // https://mvnrepository.com/artifact/org.openjdk.jol/jol-parent
    implementation("org.openjdk.jol:jol-parent:0.16")
    // https://mvnrepository.com/artifact/org.openjdk.jol/jol-samples
    implementation("org.openjdk.jol:jol-samples:0.16")
    // https://mvnrepository.com/artifact/io.netty/netty-all
    implementation("io.netty:netty-all:4.1.79.Final")

}