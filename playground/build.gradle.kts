plugins {
    alias(versions.plugins.springboot)
    alias(versions.plugins.springboot.dependency.management)
    id("java")
    id("application")
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
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // https://mvnrepository.com/artifact/org.openjdk.jol/jol-core
    implementation("org.openjdk.jol:jol-core:0.16")
    implementation("org.apache.zookeeper:zookeeper:3.6.3")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    // https://mvnrepository.com/artifact/org.apache.curator/curator-framework
    implementation("org.apache.curator:curator-framework:5.5.0")
    // https://mvnrepository.com/artifact/org.apache.curator/curator-recipes
    implementation("org.apache.curator:curator-recipes:5.5.0")

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
// https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.0")
    implementation(project(":tool-logger"))
    // https://mvnrepository.com/artifact/javax.servlet/servlet-api
    compileOnly("javax.servlet:servlet-api:2.5")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")

}
tasks.getByName<Test>("test") {
//    useJUnit() // for junit4
    useJUnitPlatform()
}
