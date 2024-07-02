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
        project.extensions.getByType<BaseAppModuleExtension>().apply {
            compileSdk = ProjectConfig.compileSdk
            namespace = ProjectConfig.namespace
            defaultConfig {
                applicationId = ProjectConfig.applicationId
                minSdk = ProjectConfig.minSk
                targetSdk = ProjectConfig.targetSdk
                versionCode = ProjectConfig.versionCode
                versionName = ProjectConfig.versionName
                testInstrumentationRunner = ProjectConfig.testInstrumentationRunner

            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            //kotlinOptions
            (this as? ExtensionAware)?.extensions?.configure<KotlinJvmOptions>(
                "kotlinOptions"
            ) {
                jvmTarget = "17"
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
        project.extensions.getByType(LibraryExtension::class.java).apply {
            compileSdk = ProjectConfig.compileSdk
            defaultConfig {
                minSdk = ProjectConfig.minSk
                testInstrumentationRunner = ProjectConfig.testInstrumentationRunner

            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            //kotlinOptions
            (this as? ExtensionAware)?.extensions?.configure<KotlinJvmOptions>(
                "kotlinOptions"
            ) {
                jvmTarget = "17"
            }
        }
        project.dependencies {
            initDependencies()
        }
    }
}