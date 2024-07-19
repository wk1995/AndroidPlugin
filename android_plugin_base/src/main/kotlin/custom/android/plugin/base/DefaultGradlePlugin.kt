package custom.android.plugin.base

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import custom.android.plugin.base.dependency.DependencyType
import custom.android.plugin.base.dependency.initDependencies
import custom.android.plugin.log.PluginLogUtil
import custom.android.plugin.push.PublishOperate
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

open class DefaultGradlePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        PluginLogUtil.printlnDebugInScreen("DefaultGradlePlugin  apply")
        setProjectConfig(target)
    }

    private fun isApp(container: PluginContainer): Boolean {
        return container.hasPlugin("com.android.application")
    }

    private fun isPlugin(container: PluginContainer): Boolean {
        return container.hasPlugin("org.gradle.kotlin.kotlin-dsl")
                || container.hasPlugin("groovy")
    }

    private fun isLibrary(container: PluginContainer) =
        container.hasPlugin("com.android.library")

    private fun setProjectConfig(project: Project) {
        if (isApp(project.plugins)) {
            PluginLogUtil.printlnDebugInScreen("this is app")
            setProjectConfigByApp(project)
        } else {
            //是 libraray
            setProjectConfigByLibrary(project)
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


    private fun setProjectConfigByLibrary(
        project: Project,
    ) {
        if (isLibrary(project.plugins)) {
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
        }

        if (isPlugin(project.plugins)) {
            PluginLogUtil.printlnDebugInScreen("is plugin")
            project.dependencies {
                //gradle sdk
                this.add(DependencyType.DEPENDENCY_TYPE_IMPLEMENTATION, gradleApi())
                //groovy sdk
                this.add(DependencyType.DEPENDENCY_TYPE_IMPLEMENTATION, localGroovy())
            }
        }
        PublishOperate.apply(project)
    }

}