plugins {
    id("java")
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.register("hello") {
    doLast {
        println("Hello world!")
    }
    doLast {
        println(project.name)
    }
}

tasks.register("count") {
    dependsOn("hello")
    doLast {
        repeat(4) { print("$it ") }
    }
}

repeat(4) { counter ->
    tasks.register("task$counter") {
        doLast {
            println("I'm task number $counter")
        }
    }
}

tasks.named("task0") {
    dependsOn("task2", "task3")
    doLast {
        println("doLast2")
    }
    doLast {
        println("doLast1")
    }

    doFirst {
        println("doFirst")
    }
}

defaultTasks("hello", "task0")
