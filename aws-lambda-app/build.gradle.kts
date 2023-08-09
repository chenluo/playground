plugins {
    id("java")
}

group = "com.chenluo"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    // https://mvnrepository.com/artifact/com.amazonaws/aws-lambda-java-core
    implementation("com.amazonaws:aws-lambda-java-core:1.2.2")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}