plugins {
    java
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}
//jar {
//    zip64=true
//    manifest {
//        attributes "Main-Class": "$mainClassName"
//    }
//    from {
//        configurations.extraLibs.collect { it.isDirectory() ? it : zipTree(it) }
//    }
//}
//
//configurations {
//    // configuration that holds jars to include in the jar
//    extraLibs
//}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
    // https://mvnrepository.com/artifact/org.apache.spark/spark-core
    implementation("org.apache.spark:spark-core_2.12:3.1.2")
    // https://mvnrepository.com/artifact/org.apache.spark/spark-mllib
    implementation("org.apache.spark:spark-mllib_2.12:3.1.2")
    // https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-mapred
    implementation("org.apache.hadoop:hadoop-mapred:0.22.0")
    // https://mvnrepository.com/artifact/org.apache.spark/spark-sql
    implementation("org.apache.spark:spark-sql_2.12:3.1.2")
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    // https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
    implementation("org.apache.kafka:kafka-clients:2.8.0")
    // https://mvnrepository.com/artifact/org.apache.spark/spark-streaming
    implementation("org.apache.spark:spark-streaming_2.12:3.1.2")
    // https://mvnrepository.com/artifact/org.apache.spark/spark-streaming-kafka-0-10
    implementation("org.apache.spark:spark-streaming-kafka-0-10_2.12:3.1.2")
    // https://mvnrepository.com/artifact/org.apache.kafka/kafka-streams-examples
    implementation("org.apache.kafka:kafka-streams-examples:2.8.0")

//    configurations.compile.extendsFrom(configurations.extraLibs)
}