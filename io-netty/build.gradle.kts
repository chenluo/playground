plugins {
    java
}

version = "2022.0806.0000"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/io.netty/netty-all
    implementation("io.netty:netty-all:4.1.79.Final") {
        exclude("io.netty:netty-tcnative")
    }
    implementation(project(":tool-logger"))
}
//configurations {
//    all*.exclude(module: "netty-tcnative")
//}