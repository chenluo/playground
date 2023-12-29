plugins {
    id("application")
}


tasks.jar {
    manifest {
        attributes("Main-Class" to "com.chenluo.FileUtil")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":tool-logger"))
}

tasks.test {
    useJUnitPlatform()
}