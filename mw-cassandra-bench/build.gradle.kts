plugins {
    id("java")
    id("me.champeau.jmh") version "0.6.7"

}


repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.datastax.cassandra/cassandra-driver-core
    implementation("com.datastax.cassandra:cassandra-driver-core:3.2.0")
    // https://mvnrepository.com/artifact/org.openjdk.jmh/jmh-core
//    jmh("org.openjdk.jmh:jmh-core:1.36")
//    implementation("org.openjdk.jmh:jmh-core:1.36")
//    jmh("org.openjdk.jmh:jmh-generator-annprocess:1.36")
//    implementation("org.openjdk.jmh:jmh-generator-annprocess:1.36")
//    jmhAnnotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.36")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}