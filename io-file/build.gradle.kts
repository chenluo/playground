plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "unspecified"

application {
    mainClass = "org.example.FileUtil"
}
tasks.jar {
    manifest {
        attributes("Main-Class" to application.mainClass)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}