rootProject.name = "java-gradle"
println("executing settings.gradle.kts")
include("playground")
include("io-netty")
include("msa-webflux")
include("lang-java")
include("msa-spring-kotlin")
include("msa-spring-cloud-service")
include("msa-spring-cloud-service-discover")
include("msa-spring-cloud-client")
include("msa-spring-cloud-gateway")
include("msa-spring")
include("msa-spring-native")
include("mw-cassandra-bench")
include("mw-elasticsearch")
include("tool-gradle")
include("tool-auto-script")
include("tool-logger")
include("mw-redis")
include("tool-snowflakeid")
include("msa-jsonrpc")
include("msa-grpc")
include("msa-batch")
include("msa-base")
include("aws-lambda-app")
include("msa-simple-springboot")
include("demo-jpa")
include("io-file")
include("msa-dgs")

// see gradle/libs.versions.toml
//dependencyResolutionManagement {
//    versionCatalogs {
//        create("versions") {
//            plugin("springboot", "org.springframework.boot").version("2.2.1")
//            plugin(
//                "springboot-dependency-management",
//                "io.spring.dependency-management"
//            ).version("0.0.11.RELEASE")
//            plugin("flyway", "org.flywaydb.flyway").version("9.1.0")
//        }
//    }
//}
include("msa-openapi-sample")

// settings.gradle.kts
pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.jetbrains.kotlin.jvm") {
                // This is where Gradle finds the version
                useVersion("1.9.22") // Or libs.versions.kotlin.get() if using a catalog
            }
        }
    }
}