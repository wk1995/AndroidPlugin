import custom.android.plugin.base.dependency.DependencyItem

plugins {
    `kotlin-dsl`
    id("android.plugin.baseBuild")
}
buildscript {
    repositories {
        mavenCentral()
    }
}
PublishInfo {
    groupId = "custom.android.plugin"
    artifactId = "picture"
    version = "0.0.1"
    pluginId = "plugin.android.picture"
    implementationClass = "custom.android.plugin.AndroidPicturePlugin"
}
dependencies {
    implementation(DependencyItem.android_build_gradle)
    implementation(DependencyItem.kotlin_gradle_plugin)
    implementation(DependencyItem.kotlinx_coroutines_core)
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.1")
    implementation("custom.android.plugin:baseBuild:latest.release")
    implementation("com.tinify:tinify:latest.release")
}
