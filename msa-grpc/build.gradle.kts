plugins {
    id("java")
    id("com.google.protobuf") version "0.9.1"
}

group = "com.chenluo"
version = "unspecified"

// IMPORTANT: You probably want the non-SNAPSHOT version of gRPC. Make sure you
// are looking at a tagged version of the example and not "master"!

// Feel free to delete the comment at the next line. It is just for safely
// updating the version in our release process.

val grpcVersion = "1.56.0" // CURRENT_GRPC_VERSION
val protobufVersion = "3.22.3"
val protocVersion = protobufVersion

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("io.grpc:grpc-protobuf:${grpcVersion}")
    implementation("io.grpc:grpc-services:${grpcVersion}")
    implementation("io.grpc:grpc-stub:${grpcVersion}")
    compileOnly("org.apache.tomcat:annotations-api:6.0.53")

    // examples/advanced need this for JsonFormat
    implementation("com.google.protobuf:protobuf-java-util:${protobufVersion}")

    runtimeOnly("io.grpc:grpc-netty-shaded:${grpcVersion}")

    testImplementation("io.grpc:grpc-testing:${grpcVersion}")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:3.4.0")
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

protobuf {
    protoc { artifact = "com.google.protobuf:protoc:${protocVersion}" }
    plugins {

    }
//    generateProtoTasks {
//        all(){ grpc {} }
//    }
}

// Inform IDEs like IntelliJ IDEA, Eclipse or NetBeans about the generated code.
sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/grpc")
            srcDirs("build/generated/source/proto/main/java")
        }
    }
}

