import com.pswidersk.gradle.python.VenvTask


version = "0.0.1"

pythonPlugin {
    pythonVersion = "3.9.2"
}

idea {
    module {
        sourceDirs.add(file("main"))
        excludeDirs.add(file("models"))

    }
}

tasks {
    register<VenvTask>("condaInfo") {
        venvExec = "conda"
        args = listOf("info")
    }

    val pipInstall by registering(VenvTask::class) {
        venvExec = "pip"
        args = listOf("install", "--isolated", "-r", "requirements.txt")
    }
}