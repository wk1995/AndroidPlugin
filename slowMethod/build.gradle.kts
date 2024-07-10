plugins {
    `kotlin-dsl`
    id("android.plugin.baseBuild")
}
gradleConfig {
    publishInfo {
        groupId = "custom.android.plugin"
        artifactId = "monitor-slowMethod"
        version = "0.0.4"
        pluginId = "plugin.android.monitor.slow.method"
        implementationClass = "custom.android.plugin.SlowMethodMonitorPlugin"
    }
}

/*PublishInfo {
    groupId = "custom.android.plugin"
    artifactId = "monitor-slowMethod"
    version = "0.0.2"
    pluginId = "plugin.android.monitor.slow.method"
    implementationClass = "custom.android.plugin.SlowMethodMonitorPlugin"
}*/
/*
dependencies {
    //gradle sdk
    implementation(gradleApi())
    //groovy sdk
    implementation(localGroovy())
}*/
