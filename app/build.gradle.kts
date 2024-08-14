plugins {
    id("com.android.application")
    id("android.plugin.baseBuild")
    id("plugin.android.monitor.slow.method")
}
AsmConfig {
    enabled = true
}

android {
    namespace = "com.wk.plugin"
    defaultConfig {
        applicationId = "com.wk.plugin"
        versionCode = 1
        versionName = "2.0.0"
    }

    packagingOptions {
        // 排除重复的 META-INF 文件
        exclude("META-INF/kotlinx_coroutines_core.version")

    }
    packaging {
        exclude("META-INF/kotlinx_coroutines_core.version")
    }

}

dependencies {
}