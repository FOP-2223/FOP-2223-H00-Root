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
    toolVersion.set("0.6.0-SNAPSHOT")
    grader {
        graderName.set("FOP-2223-H00")
        assignmentId.set("h00")
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
//    "graderPublicImplementation"(libs.jagr.launcher) {
//        exclude("org.jetbrains", "annotations")
//    }
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
