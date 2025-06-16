plugins {
    java
}

group = "com.chenluo"
version = "1.0-SNAPSHOT"

subprojects {
    apply(plugin = "java")
    repositories {
        mavenCentral()
    }
    plugins.withType<JavaPlugin> { // Apply only to subprojects that have the Java plugin applied
        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(17)) // Redundant but explicit. Inherits from root anyway.
            }
        }
    }
}
