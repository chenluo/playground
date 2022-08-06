plugins {
    java
}

version = "2022.0806.0000"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    // testImplementation("org.springframework.boot:spring-boot-aop")
    implementation("org.slf4j:slf4j-api:1.7.30")
    // https://mvnrepository.com/artifact/io.netty/netty-all
    implementation("io.netty:netty-all:4.1.79.Final") {
        exclude("io.netty:netty-tcnative")
    }
    // https://mvnrepository.com/artifact/org.springframework/spring-beans
    implementation("org.springframework:spring-beans:5.3.9")
    // https://mvnrepository.com/artifact/org.springframework/spring-beans
    implementation("org.springframework:spring-core:5.3.9")

}
//configurations {
//    all*.exclude(module: "netty-tcnative")
//}