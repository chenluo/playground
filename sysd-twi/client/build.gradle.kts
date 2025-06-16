plugins {
    alias(libs.plugins.springboot)
    alias(libs.plugins.springboot.dependency.management)
    application
}

group = "com.chenluo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    // Specify the fully qualified name of your main class
    mainClass.set("com.chenluo.UserAgent")
    // Or for a multi-module project where this is a subproject:
    // mainClass.set("com.example.yourproject.module_name.YourMainClass")
}

// Optional: Configure JVM arguments or application arguments for the 'run' task
tasks.named<JavaExec>("run") {
    // Example JVM arguments (e.g., for memory settings or system properties)
    jvmArgs("-Xmx2G", "-Dmy.system.property=someValue")

    // Example application arguments (passed to your main method's args[])
//    args("arg1", "arg2", "--config=dev")
}

tasks.test {
    useJUnitPlatform()
}