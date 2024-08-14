System.out.println("build.gradle.kts")
plugins {
    `kotlin-dsl`
    id("android.plugin.baseBuild")
}
System.out.println("build.gradle.kts gradleConfig" )
PublishInfo {
    groupId = "custom.android.plugin"
    artifactId = "monitor-slowMethod"
    version = "0.0.4"
    pluginId = "plugin.android.monitor.slow.method"
    implementationClass = "custom.android.plugin.SlowMethodMonitorPlugin"
}

dependencies {
    implementation("org.ow2.asm:asm:9.5")
    implementation("org.ow2.asm:asm-util:9.5")
    implementation("org.ow2.asm:asm-commons:9.5")
    implementation("org.ow2.asm:asm-analysis:9.5")
    implementation("org.ow2.asm:asm-tree:9.5")
    implementation(gradleApi())
    //groovy sdk
    implementation(localGroovy())
    implementation("com.android.tools.build:gradle:8.1.3")
    implementation("custom.android.plugin:baseBuild:latest.release")
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
