plugins {
    id("org.springframework.boot") version "2.5.15"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
    id("application")
    id("java-library")
    id("com.netflix.dgs.codegen") version "5.1.16"
}

tasks.generateJava {
    generateClient = true
    typeMapping = mutableMapOf(
        "BigDecimal" to "java.math.BigDecimal",
        "JSON" to "com.fasterxml.jackson.databind.JsonNode",
        "Object" to "com.fasterxml.jackson.databind.JsonNode"
        )

}

dependencyManagement {
    imports {
        mavenBom("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:4.10.3")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
    implementation("com.graphql-java:graphql-java-extended-scalars:21.0")
    // https://mvnrepository.com/artifact/io.netty/netty-resolver-dns-native-macos
    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.106.Final:osx-aarch_64")
// https://mvnrepository.com/artifact/io.projectreactor/reactor-core
    implementation("io.projectreactor:reactor-core:3.4.25")
// https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1")

    implementation(project(":tool-logger"))
}

tasks.test {
    useJUnitPlatform()
}