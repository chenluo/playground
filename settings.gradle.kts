rootProject.name = "java-gradle"
println("executing settings.gradle.kts")
include("playground")
include("io-netty")
include("msa-webflux")
include("lang-java")
include("msa-spring-kotlin")
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
include("msa-simple-springboot")
include("demo-jpa")
include("io-file")
include("msa-dgs")

dependencyResolutionManagement {
    versionCatalogs {
        create("versions") {
            plugin("springboot", "org.springframework.boot").version("3.2.1")
            plugin(
                "springboot-dependency-management",
                "io.spring.dependency-management"
            ).version("1.0.11.RELEASE")
            plugin("flyway", "org.flywaydb.flyway").version("10.1.0")
        }
    }
}
include("msa-openapi-sample")
