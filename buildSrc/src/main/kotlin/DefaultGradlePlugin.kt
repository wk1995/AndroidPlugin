import com.android.build.gradle.AppExtension
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import dependency.affectiveSdk
import dependency.initDependencies
import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

open class DefaultGradlePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        setProjectConfig(target)
    }

    private fun setProjectConfig(project: Project) {
        val isApp = project.plugins.hasPlugin("com.android.application")
        if (isApp) {
            //是app
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
        val projectConfigInfo = try {
            project.extensions.getByType(ProjectConfigInfo::class.java)
        } catch (e: Exception) {
            PluginLogUtil.printlnErrorInScreen("getByType ProjectConfigInfo error ${e.message}")
            ProjectConfigInfo()
        }
        PluginLogUtil.printlnDebugInScreen("projectConfigInfo: $projectConfigInfo")
        project.extensions.getByType<BaseAppModuleExtension>().apply {
            compileSdk = projectConfigInfo.compileSdk
            namespace = projectConfigInfo.namespace
            defaultConfig {
                applicationId = projectConfigInfo.applicationId
                minSdk = projectConfigInfo.minSk
                targetSdk = projectConfigInfo.targetSdk
                versionCode = projectConfigInfo.versionCode
                versionName = projectConfigInfo.versionName
                testInstrumentationRunner = projectConfigInfo.testInstrumentationRunner
            }
            compileOptions {
                sourceCompatibility = projectConfigInfo.javeVersion
                targetCompatibility = projectConfigInfo.javeVersion
            }
            //kotlinOptions
            (this as? ExtensionAware)?.extensions?.configure<KotlinJvmOptions>(
                "kotlinOptions"
            ) {
                jvmTarget = projectConfigInfo.getJavaTarget()
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
                }
                debug {
                    isDebuggable = true
                    isMinifyEnabled = false
                    isShrinkResources = false
                    isJniDebuggable = true
                }

            }
        }
        project.dependencies {
            initDependencies()
            affectiveSdk()
        }
    }


    private fun setProjectConfigByLibrary(project: Project) {
        project.apply {
            plugin("kotlin-android")
            plugin("kotlin-kapt")
            plugin("org.jetbrains.kotlin.android")
        }
        val projectConfigInfo = try {
            project.extensions.getByType(ProjectConfigInfo::class.java)
        } catch (e: Exception) {
            ProjectConfigInfo()
        }
        project.extensions.getByType(LibraryExtension::class.java).apply {
            compileSdk = projectConfigInfo.compileSdk
            defaultConfig {
                minSdk = projectConfigInfo.minSk
                testInstrumentationRunner = projectConfigInfo.testInstrumentationRunner

            }
            compileOptions {
                sourceCompatibility = projectConfigInfo.javeVersion
                targetCompatibility = projectConfigInfo.javeVersion
            }
            //kotlinOptions
            (this as? ExtensionAware)?.extensions?.configure<KotlinJvmOptions>(
                "kotlinOptions"
            ) {
                jvmTarget = projectConfigInfo.getJavaTarget()
            }
        }
        project.dependencies {
            initDependencies()
        }
    }
}