package custom.android.plugin.base

import org.gradle.api.JavaVersion


/**
 * 项目编译配置与AppId配置
 * */
object ProjectConfig {

    const val MIN_SDK_VERSION = 23
    const val COMPILE_SDK_VERSION = 34
    const val TARGET_SDK_VERSION = 34
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJunitRunner"
    val DEFAULT_JAVA_VERSION = JavaVersion.VERSION_17
    fun getJavaTarget(javeVersion: JavaVersion): String {
        return when (javeVersion) {
            JavaVersion.VERSION_17 -> {
                "17"
            }

            else -> {
                "1.8"
            }
        }
    }

}