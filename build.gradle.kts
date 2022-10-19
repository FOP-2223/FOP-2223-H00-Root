@Suppress("DSL_SCOPE_VIOLATION") // https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    java
    application
    alias(libs.plugins.style)
    id("org.sourcegrade.jagr-gradle") version "0.6.0-SNAPSHOT"
}

version = file("version").readLines().first()

jagr {
    assignmentId.set("h00")
    submissions {
        val main by creating {
            studentId.set("ab12cdef")
            firstName.set("sol_first")
            lastName.set("sol_last")
        }
    }
    graders {
        val graderPublic by creating {
            graderName.set("FOP-2223-H00-Public")
            rubricProviderName.set("h00.H00_RubricProvider")
        }
        val graderPrivate by creating {
            parent(graderPublic)
            graderName.set("FOP-2223-H00-Private")
        }
    }
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
