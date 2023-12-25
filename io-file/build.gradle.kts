plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "unspecified"

tasks.jar {
    manifest {
        attributes("Main-Class" to "com.chenluo.FileUtil")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(project(":tool-logger"))
}

tasks.test {
    useJUnitPlatform()
}