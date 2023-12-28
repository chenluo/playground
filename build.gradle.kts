import java.time.Instant

plugins {
    id("java")
}
allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
    group = "com.chenluo"
    version = Instant.now().toEpochMilli()

}

subprojects {
    apply(plugin = "java")
    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
    }
}
