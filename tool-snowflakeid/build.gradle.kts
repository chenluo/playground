dependencies {
    // https://mvnrepository.com/artifact/org.apache.curator/curator-framework
    implementation("org.apache.curator:curator-framework:5.5.0")
    testImplementation("org.testcontainers:testcontainers:1.18.3")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    minHeapSize = "10G"
    maxHeapSize = "20G"
}

