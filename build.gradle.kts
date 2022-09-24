import org.sourcegrade.jagr.launcher.executor.createProgressBarProvider

@Suppress("DSL_SCOPE_VIOLATION") // https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    java
    application
    alias(libs.plugins.style)
    alias(libs.plugins.submitter)
    id("org.sourcegrade.jagr-gradle") version "0.6.0-SNAPSHOT"
}

version = file("version").readLines().first()

jagr {
    assignmentId.set("h00")
    graders {
        val graderPublic by creating {
            graderName.set("FOP-2223-H00-Public")
        }
        val graderPrivate by creating {
            graderName.set("FOP-2223-H00-Private")
            dependsOn(graderPublic.sourceSet)
        }
    }
    submissions {

    }
}

submit {
    assignmentId = "h00"
    studentId = "ab12cdef"
    firstName = "sol_first"
    lastName = "sol_last"
    requireTests = false
}

dependencies {
    implementation(libs.annotations)
    testImplementation(libs.junit.core)
    implementation("org.sourcegrade:fopbot:0.3.0")
}

application {
    mainClass.set("h00.Main")
}

tasks {
    val runDir = File("build/run")
    named<JavaExec>("run") {
        doFirst {
            runDir.mkdirs()
        }
        workingDir = runDir
    }
    test {
        doFirst {
            runDir.mkdirs()
        }
        workingDir = runDir
        useJUnitPlatform()
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    jar {
        enabled = false
    }
}
