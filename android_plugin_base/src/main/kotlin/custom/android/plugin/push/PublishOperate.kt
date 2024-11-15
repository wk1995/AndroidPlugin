package custom.android.plugin.push

import com.android.build.gradle.LibraryExtension
import custom.android.plugin.base.ModuleType
import custom.android.plugin.log.PluginLogUtil
import custom.android.plugin.push.BasePublishTask.Companion.MAVEN_PUBLICATION_NAME
import org.gradle.api.Project
import org.gradle.api.component.SoftwareComponent
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.jvm.tasks.Jar
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import java.net.URI
import java.util.Properties


open class PublishOperate {
    companion object {
        private const val TAG = "PublishOperate"
    }

    private val properties by lazy {
        Properties()// local.properties file in the root director
    }

    fun <T : PublishInfoExtension> configPublish(project: Project, type: Int, clazz: Class<T>) {
        // use Gradle Maven plugins
        if (type == ModuleType.APP) {
            PluginLogUtil.printlnDebugInScreen("$TAG is app")
            return
        }
        project.plugins.apply(MavenPublishPlugin::class.java)
        project.extensions.create(
            PublishInfoExtension.EXTENSION_PUBLISH_INFO_NAME, clazz,
        )
        project.afterEvaluate {
            try {
                val publishInfo = project.extensions.getByType(clazz)
                val publishing = project.extensions.getByType(PublishingExtension::class.java)
                components.forEach {
                    PluginLogUtil.printlnDebugInScreen("$TAG name: ${it.name}")
                    if (type == ModuleType.PLUGIN) {
                        if (it.name == "java") {
                            val gradlePluginDevelopmentExtension =
                                project.extensions.getByType(GradlePluginDevelopmentExtension::class.java)
                            gradlePluginDevelopmentExtension.plugins {
                                create("gradlePluginCreate") {
                                    // 插件ID
                                    id = publishInfo.pluginId
                                    // 插件的实现类
                                    implementationClass = publishInfo.implementationClass
                                }
                            }
                            publishing(project, publishing, publishInfo, it)
                        }
                    }
                    if (type == ModuleType.LIBRARY) {
                        if (it.name == "release") {
                            //注册上传task
                            publishing(project, publishing, publishInfo, it)
                        }
                    }
                }

            } catch (e: Exception) {
                PluginLogUtil.printlnErrorInScreen("$TAG PluginModule error ${e.message}")
            }


        }
        val currProjectName = project.displayName
        PluginLogUtil.printlnDebugInScreen("$TAG currProjectName $currProjectName")
        project.gradle.afterProject {
            PluginLogUtil.printlnDebugInScreen("$TAG currProject.displayName $displayName")
            if (currProjectName == displayName) {
                PluginLogUtil.printlnDebugInScreen("$TAG $currProjectName start register ")
                project.tasks.register(
                    PublishLibraryLocalTask.TAG, PublishLibraryLocalTask::class.java
                )
                project.tasks.register(
                    PublishLibraryRemoteTask.TAG, PublishLibraryRemoteTask::class.java
                )
            }
        }
    }

    private fun <T : PublishInfoExtension> publishing(
        project: Project,
        publishing: PublishingExtension,
        publishInfo: T,
        softwareComponent: SoftwareComponent
    ) {
        properties.load(project.rootProject.file("local.properties").inputStream())
        PluginLogUtil.printlnDebugInScreen("properties: $properties")
        publishing.publications {
            create(
                MAVEN_PUBLICATION_NAME, MavenPublication::class.java
            ) {
                groupId = publishInfo.groupId
                artifactId = publishInfo.artifactId
                version = publishInfo.version
                if (version.endsWith("-debug")) {
                    val taskName = "androidSourcesJar"
                    //获取build.gradle中的android节点
                    val androidSet = project.extensions.getByName("android") as LibraryExtension
                    val sourceSet = androidSet.sourceSets
                    //获取android节点下的源码目录
                    val sourceSetFiles = sourceSet.findByName("main")?.java?.srcDirs
                    val task = project.tasks.findByName(taskName) ?: project.tasks.create(
                        taskName, Jar::class.java
                    ) {
                        from(sourceSetFiles)
                        archiveClassifier.set("sources")
                    }
                    artifact(task)
                }
                from(softwareComponent)
            }
        }
        var publishUrl = publishInfo.getPublishUrl()
        if (publishUrl.isEmpty()) {
            publishUrl = properties.getProperty("publishUrl", "")
        }
        var publishUserName = publishInfo.getPublishUserName()
        if (publishUserName.isEmpty()) {
            publishUserName = properties.getProperty("publishUserName", "")
        }
        var publishPassword = publishInfo.getPublishPassword()
        if (publishPassword.isEmpty()) {
            publishPassword = properties.getProperty("publishPassword", "")
        }
        PluginLogUtil.printlnDebugInScreen("$TAG publishUrl is $publishUrl")
        PluginLogUtil.printlnDebugInScreen("$TAG publishUserName is $publishUserName  publishPassword is $publishPassword")
        if (publishUrl.isEmpty()) {
            publishUrl = getDefaultPublishUrl()
        }
        if (publishUserName.isEmpty()) {
            publishUserName = getDefaultPublishUserName()
        }
        if (publishPassword.isEmpty()) {
            publishPassword = getDefaultPublishPassword()
        }
        if (publishUrl.isNotEmpty()) {
            publishing.repositories {
                maven {
                    url = URI(publishUrl)
                    isAllowInsecureProtocol = true
                    credentials { ->
                        username = publishUserName
                        password = publishPassword
                    }
                }
            }
        }
    }

    open fun getDefaultPublishUrl(): String = ""

    open fun getDefaultPublishUserName(): String = ""

    open fun getDefaultPublishPassword(): String = ""

}