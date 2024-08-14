package custom.android.plugin.base

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import custom.android.plugin.base.dependency.DependencyType
import custom.android.plugin.base.dependency.initDependencies
import custom.android.plugin.log.PluginLogUtil
import custom.android.plugin.push.PublishInfoExtension
import custom.android.plugin.push.PublishOperate
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

open class DefaultGradlePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        PluginLogUtil.printlnDebugInScreen("DefaultGradlePlugin  apply")
        setProjectConfig(target)
    }

    private fun setProjectConfig(project: Project) {
        val container = project.plugins
        if (ProjectHelper.isApp(container)) {
            PluginLogUtil.printlnDebugInScreen("this is app")
            setProjectConfigByApp(project)
        }

        if (ProjectHelper.isLibrary(container)) {
            PluginLogUtil.printlnDebugInScreen("this is library")
            setProjectConfigByLibrary(project)
        }

        if (ProjectHelper.isPlugin(container)) {
            PluginLogUtil.printlnDebugInScreen("this is plugin")
            setProjectConfigByPlugin(project)
        }
    }

    private fun setProjectConfigByApp(project: Project) {
        project.apply {
            plugin("kotlin-android")
            plugin("kotlin-kapt")
            plugin("org.jetbrains.kotlin.android")
        }

        project.extensions.getByType<BaseAppModuleExtension>(BaseAppModuleExtension::class.java)
            .apply {
                compileSdk = ProjectConfig.COMPILE_SDK_VERSION
                defaultConfig {
                    minSdk = ProjectConfig.MIN_SDK_VERSION
                    targetSdk = ProjectConfig.TARGET_SDK_VERSION
                    testInstrumentationRunner = ProjectConfig.TEST_INSTRUMENTATION_RUNNER
                }
                val javaVersion = ProjectConfig.DEFAULT_JAVA_VERSION
                compileOptions {
                    sourceCompatibility = javaVersion
                    targetCompatibility = javaVersion
                }
                //kotlinOptions
                (this as? ExtensionAware)?.extensions?.configure<KotlinJvmOptions>(
                    "kotlinOptions"
                ) {
                    jvmTarget = ProjectConfig.getJavaTarget(javaVersion)
                }
                buildTypes {
                    release {
                        isDebuggable = false
                        isMinifyEnabled = true
                        isShrinkResources = true
                        isJniDebuggable = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                        signingConfig = signingConfigs.findByName("release")
                    }
                    debug {
                        isDebuggable = true
                        isMinifyEnabled = false
                        isShrinkResources = false
                        isJniDebuggable = true
                        signingConfig = signingConfigs.findByName("debug")
                    }

                }
            }
        project.dependencies {
            initDependencies()
        }
    }

    private fun setProjectConfigByPlugin(project: Project) {
        PluginLogUtil.printlnDebugInScreen("is plugin")
        project.dependencies {
            //gradle sdk
            this.add(DependencyType.DEPENDENCY_TYPE_IMPLEMENTATION, gradleApi())
            //groovy sdk
            this.add(DependencyType.DEPENDENCY_TYPE_IMPLEMENTATION, localGroovy())
        }
        PublishOperate.configPublish(project, ModuleType.PLUGIN, getPublishInfoExtension())
    }

    open fun getPublishInfoExtension(): Class<out PublishInfoExtension> {
        return PublishInfoExtension::class.java
    }


    private fun setProjectConfigByLibrary(
        project: Project
    ) {
        PluginLogUtil.printlnDebugInScreen("is library")
        project.apply {
            plugin("kotlin-android")
            plugin("kotlin-kapt")
            plugin("org.jetbrains.kotlin.android")
        }
        project.extensions.getByType(LibraryExtension::class.java).apply {
            compileSdk = ProjectConfig.COMPILE_SDK_VERSION
            defaultConfig {
                minSdk = ProjectConfig.MIN_SDK_VERSION
                testInstrumentationRunner = ProjectConfig.TEST_INSTRUMENTATION_RUNNER

            }
            val javaVersion = ProjectConfig.DEFAULT_JAVA_VERSION
            compileOptions {
                sourceCompatibility = javaVersion
                targetCompatibility = javaVersion
            }
            //kotlinOptions
            (this as? ExtensionAware)?.extensions?.configure<KotlinJvmOptions>(
                "kotlinOptions"
            ) {
                jvmTarget = ProjectConfig.getJavaTarget(javaVersion)
            }
        }
        project.dependencies {
            initDependencies()
        }
        PublishOperate.configPublish(project, ModuleType.LIBRARY, getPublishInfoExtension())
    }

}