dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        mavenLocal()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "FOP-2223-H00-Root"
sourceControl {
    gitRepository(java.net.URI("https://github.com/TUDalgo/Algo-Utils.git")) {
        producesModule("algoutils:algoutils-student")
//        producesModule("algoutils:algoutils-tutor")
    }
}
