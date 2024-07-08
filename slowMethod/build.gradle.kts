plugins {
    `kotlin-dsl`
    id("maven-publish")
    id("custom.android.plugin")
}

PublishInfo {
    groupId = "custom.android.plugin"
    artifactId = "monitor-slowMethod"
    version = "0.0.2"
    pluginId = "plugin.android.monitor.slow.method"
    implementationClass = "custom.android.plugin.SlowMethodMonitorPlugin"
}
dependencies {
    //gradle sdk
    implementation(gradleApi())
    //groovy sdk
    implementation(localGroovy())
}