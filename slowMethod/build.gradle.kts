plugins {
    `kotlin-dsl`
    id("maven-publish")
    id("custom.android.plugin")
}

PublishInfo {
    groupId = "custom.android.plugin"
}
dependencies {
    //gradle sdk
    implementation(gradleApi())
    //groovy sdk
    implementation(localGroovy())
}

gradlePlugin {
    plugins {
        create("slowMethodMonitorPlugin") {
            // 插件ID
            id = "plugin.android.monitor.slow.method"
            // 插件的实现类
            implementationClass = "custom.android.plugin.SlowMethodMonitorPlugin"
        }
    }

}

publishing {
    publications {
        create<MavenPublication>("SlowMethodMonitorPlugin") {
            groupId = "custom.android.plugin"
            artifactId = "monitor-slowMethod"
            version = "0.0.1"
            from(components["java"])
        }
    }
}
