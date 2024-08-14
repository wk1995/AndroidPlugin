// Top-level build file where you can add configuration options common to all sub-projects/modules.
/*plugins {
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.android.library") version "8.1.3" apply false
}*/

buildscript {
    repositories {
        mavenLocal()
        maven("https://jitpack.io")
        google()
        mavenCentral()
    }
    val hasBasePlugin = try {
        gradle.startParameter.projectProperties["hasBasePlugin"]?.toBoolean() ?: false
    } catch (e: Exception) {
        false
    }
    System.out.println("isFirstRunning: ${gradle.startParameter.projectProperties}")
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        if (hasBasePlugin) {
            classpath("custom.android.plugin:baseBuild:latest.release")
            classpath ("custom.android.plugin:monitor-slowMethod:0.0.4")
        }
    }
}