plugins {
    id("java")
}


repositories {
    mavenCentral()
}

dependencies {
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
