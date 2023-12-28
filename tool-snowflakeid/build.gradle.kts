plugins {
    id("java")
}


repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.apache.curator/curator-framework
    implementation("org.apache.curator:curator-framework:5.5.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    minHeapSize = "10G"
    maxHeapSize = "20G"
}

