plugins {
    id("java")
}


repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.amazonaws/aws-lambda-java-core
    implementation("com.amazonaws:aws-lambda-java-core:1.2.2")

}


tasks {
    val copyRuntimeDependencies by registering(Copy::class) {
        from(configurations.runtimeClasspath)
        into("build/dependency")
    }
    build {
        dependsOn(copyRuntimeDependencies)
    }
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}