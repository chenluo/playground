rootProject.name = "java-gradle"
println("executing settings.gradle.kts")
include("playground")
include("io-netty")
include("io-webflux")
include("lang-jvm")
include("lang-kotlin")
include("msa-service")
include("msa-service-discover")
include("msa-service-feign-client")
include("msa-spring-cloud")
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
include("demo-jpa")

dependencyResolutionManagement {
    versionCatalogs {
        create("versions") {
            plugin("springboot", "org.springframework.boot").version("3.0.1")
            plugin("springboot-dependency-management",
                "io.spring.dependency-management").version("1.0.11.RELEASE")
            plugin("flyway", "org.flywaydb.flyway").version("9.8.1")
        }
    }
}
include("io-file")
