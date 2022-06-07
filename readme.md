# FOP-2223-H00-Root
Das Lösungsrepository zu Hausübung 00 der FoP im Wintersemester 2022/2023

## Grading
### den Grader Bauen:
- Unix-Basierende Systeme:
```sh
./gradlew graderAll
```
- Windows:
```bat
./gradlew.bat graderAll
```
### Tests im IDE ausführen
Wenn die Abgaben mit dem Autograder exportiert wurden, muss die `build.gradle.kts` im Abgabeordner entsprechend angepasst werden:
```kt
plugins {
  java
}
allprojects {
  apply(plugin = "java")
  apply(plugin = "application")
  repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
  }
  dependencies {
    implementation("org.jetbrains:annotations:23.0.0")
    implementation("org.sourcegrade:jagr-launcher:0.4.0")
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
  }
  java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  tasks {
    create<Jar>("prepareSubmission") {
      group = "submit"
      from(sourceSets.main.get().allSource)
      archiveFileName.set("${project.name}-submission.jar")
    }
    test {
      useJUnitPlatform()
    }
  }
}
