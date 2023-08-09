plugins {
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
    id("java-gradle.java-application-conventions")
}

group = "com.chenluo"
version = "unspecified"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.chenluo.BatchApp")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.springframework.boot:spring-boot-starter-batch")
//    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.28")
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
        attributes["Main-Class"] = "com.chenluo.BatchApp" // fully qualified class name of default main class
    }
    from(configurations.runtimeClasspath.get().map({ if (it.isDirectory) it else zipTree(it) }))
    with(tasks["jar"] as CopySpec)
}

