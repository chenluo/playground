plugins {
    alias(versions.plugins.springboot)
    alias(versions.plugins.springboot.dependency.management)
    id("java")
    id("application")
}

application {
    mainClass.set("com.chenluo.BatchApp")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-batch")
//    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.28")
    // https://mvnrepository.com/artifact/software.amazon.awssdk/dynamodb
    implementation(project(":msa-base"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

val fatJar = task("fatJar", type = Jar::class) {
    dependsOn.addAll(
        listOf(
            "compileJava",
            "processResources"
        )
    ) // We need this for Gradle optimization to work
    archiveClassifier.set("standalone") // Naming the jar
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    // manifest Main-Class attribute is optional.
    // (Used only to provide default main class for executable jar)
    manifest {
        attributes["Main-Class"] =
            "com.chenluo.BatchApp" // fully qualified class name of default main class
    }
    from(
        configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) }
    )
    with(tasks["jar"] as CopySpec)
}

