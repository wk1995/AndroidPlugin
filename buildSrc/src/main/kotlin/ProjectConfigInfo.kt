import org.gradle.api.JavaVersion


/**
 * 项目编译配置与AppId配置
 * */
class ProjectConfigInfo() {

    companion object {
        private const val MIN_SDK_VERSION = 23
        private const val COMPILE_SDK_VERSION = 34
        private const val TARGET_SDK_VERSION = 34
        private const val VERSION_CODE = 100
        private const val VERSION_NAME = "1.0.0"
        private const val APPLICATION_ID = "com.wk.plugin"
        private const val NAME_SPACE = "com.wk.plugin"
        private const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJunitRunner"
        private val DEFAULT_JAVA_VERSION = JavaVersion.VERSION_17

    }

    var minSk = MIN_SDK_VERSION
    var compileSdk = COMPILE_SDK_VERSION
    var targetSdk = TARGET_SDK_VERSION
    var versionCode = VERSION_CODE
    var versionName = VERSION_NAME
    var applicationId = APPLICATION_ID
    var namespace = NAME_SPACE
    var testInstrumentationRunner = TEST_INSTRUMENTATION_RUNNER
    var javeVersion: JavaVersion = DEFAULT_JAVA_VERSION

    constructor(
        minSk: Int = MIN_SDK_VERSION,
        compileSdk: Int = COMPILE_SDK_VERSION,
        targetSdk: Int = TARGET_SDK_VERSION,
        versionCode: Int = VERSION_CODE,
        versionName: String = VERSION_NAME,
        applicationId: String = APPLICATION_ID,
        namespace: String = NAME_SPACE,
        testInstrumentationRunner: String = TEST_INSTRUMENTATION_RUNNER,
        javeVersion: JavaVersion = DEFAULT_JAVA_VERSION
    ) : this() {
        this.minSk = minSk
        this.compileSdk = compileSdk
        this.targetSdk = targetSdk
        this.versionCode = versionCode
        this.versionName = versionName
        this.applicationId = applicationId
        this.namespace = namespace
        this.testInstrumentationRunner = testInstrumentationRunner
        this.javeVersion = javeVersion
    }


    fun getJavaTarget(): String {
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