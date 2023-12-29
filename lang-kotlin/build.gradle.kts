plugins {
    id("application")
    alias(versions.plugins.springboot)
    alias(versions.plugins.springboot.dependency.management)
}

application {
    mainClass.set("com.chenluo.test.kotlin.App")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation("io.mockk:mockk:1.12.8")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.21")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.retry:spring-retry:2.0.0")
    implementation("org.springframework:spring-aspects")

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.getByName<Test>("test") {
//    useJUnit() // for junit4
    useJUnitPlatform()
}
