
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

// 使用自定义插件
apply<DefaultGradlePlugin>()

android {
//    namespace = "com.wk.plugin"
}

dependencies {
}