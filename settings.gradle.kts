import java.io.FilenameFilter

pluginManagement {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }
}

rootProject.name = "MyPlugin"
val isFirst = true
if (isFirst) {
    include(":android_plugin_base")
} else {
    val filter =
        FilenameFilter { _, name ->
            name?.endsWith(".gradle") ?: false || name?.endsWith(".gradle.kts") ?: false
        }
    rootDir.listFiles()?.forEach {
        if (it.isDirectory && it.name != "buildSrc" && it.listFiles(filter)?.isNotEmpty() == true) {
            include(it.name)
        }
    }
}


