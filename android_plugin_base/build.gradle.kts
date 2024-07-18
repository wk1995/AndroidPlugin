plugins {
    `kotlin-dsl`
    id("maven-publish")
}

dependencies {
    //gradle sdk
    implementation(gradleApi())
    //groovy sdk
    implementation(localGroovy())
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    implementation("com.android.tools.build:gradle:8.1.3")
}

gradlePlugin {
    plugins {
        create("customPlugin") {
            // 插件ID
            id = "android.plugin.baseBuild"
            // 插件的实现类
            implementationClass = "custom.android.plugin.base.DefaultGradlePlugin"
        }
    }

}

publishing {
    publications {
        create<MavenPublication>("customPlugin") {
            groupId = "custom.android.plugin"
            artifactId = "baseBuild"
            version = "0.0.1"
            from(components["java"])
        }
    }
}
