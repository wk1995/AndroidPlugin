import custom.android.plugin.base.dependency.DependencyItem

plugins {
    `kotlin-dsl`
    id("android.plugin.baseBuild")
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
}
