import java.time.Instant

plugins {
    id("java")
    kotlin("jvm") version "1.9.20"
    id("com.google.protobuf") version "0.9.1"
    kotlin("plugin.spring") version "1.9.20"
    kotlin("plugin.jpa") version "1.9.20"
    id("com.diffplug.spotless") version "6.23.3"
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
    apply(plugin = "com.diffplug.spotless")
    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
    }
    configurations.all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
    }
    spotless {
        // optional: limit format enforcement to just the files changed by this feature branch
//   ratchetFrom 'origin/main'

        format("misc") {
            // define the files to apply `misc` to
            target("*.gradle", "*.md", ".gitignore")

            // define the steps to apply to those files
            trimTrailingWhitespace()
            indentWithSpaces(4) // or spaces. Takes an integer argument if you don't like 4
            endWithNewline()
            setEncoding("utf-8")
        }
        java {
            targetExclude("build/")
//            eclipse() // I like eclipse formatting over google
            googleJavaFormat().aosp().reflowLongStrings().formatJavadoc(false)
                .reorderImports(true)
        }
        kotlin {
            ktfmt()
        }
    }
}

tasks.jar {
    enabled = false
}
