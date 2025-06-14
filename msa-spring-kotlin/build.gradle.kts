plugins {
    id("application")
    alias(libs.plugins.springboot)
    alias(libs.plugins.springboot.dependency.management)
    kotlin("jvm")
}

application {
    mainClass.set("com.chenluo.test.kotlin.App")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation("io.mockk:mockk:1.12.8")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.21")
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.retry:spring-retry:2.0.0")
    implementation("org.springframework:spring-aspects")
    // https://mvnrepository.com/artifact/io.mockk/mockk
    testImplementation("io.mockk:mockk:1.13.7")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
}

tasks.getByName<Test>("test") {
//    useJUnit() // for junit4
    useJUnitPlatform()
}
