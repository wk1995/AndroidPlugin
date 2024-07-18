package custom.android.plugin.push

import custom.android.plugin.log.PluginLogUtil
import org.gradle.api.DefaultTask
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.TaskAction
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.StringBuilder

abstract class BasePublishTask : DefaultTask() {
    //检验状态是否通过
    private var checkStatus = false

    /**
     * 不能写成get/set
     * */
    abstract fun initPublishCommandLine(): String

    companion object {
        private const val TAG = "BasePublishTask"
        const val MAVEN_PUBLICATION_NAME = "myRelease"
    }

    init {
        group = "customPlugin"
    }


    @TaskAction
    fun doTask() {
        executeTask()
    }

    protected fun executeTask() {
        val publishInfo = project.extensions.getByType(PublishInfoExtension::class.java)

        //1、对publisher配置的信息进行基础校验
        //2、把publisher上传到服务器端，做版本重复性校验
        checkStatus = checkPublishInfo(publishInfo)
        //如果前两步都校验通过了，checkStatus设置为true
//        PluginLogUtil.printlnDebugInScreen("projectDir: ${project.projectDir.absolutePath}")
//        PluginLogUtil.printlnDebugInScreen("rootDir: ${project.rootDir.absolutePath}")
        val realTaskName =
            project.projectDir.absolutePath
                .removePrefix(project.rootDir.absolutePath)
                .replace(File.separator, ":") + initPublishCommandLine()

        if (checkStatus) {
            val out = ByteArrayOutputStream()
            val osName = System.getProperty("os.name")
            PluginLogUtil.printlnInfoInScreen("current System is :$osName")
            val gradlewFileName = if (osName.contains("Windows")) {
                // Windows 系统
                "gradlew.bat"
            } else if (osName.contains("Mac")) {
                // macOS 系统
                "gradlew"
            } else if (osName.contains("Linux")) {
                // Linux 系统
                "gradlew.bat"
            } else {
                ""
            }
            val path = "${project.rootDir}${File.separator}${gradlewFileName}"
            PluginLogUtil.printlnDebugInScreen("$TAG path: $path realTaskName: $realTaskName")
            //通过命令行的方式进行调用上传maven的task
            project.exec {
                this.standardOutput = out
                this.setCommandLine(
                    path,
                    realTaskName
                )
            }
            val result = out.toString()

            if (result.contains("UP-TO-DATE")) {
                //上传maven仓库成功，上报到服务器
                val isSuccess = requestUploadVersion()
                if (isSuccess) {
                    val publishing = project.extensions.getByType(PublishingExtension::class.java)
                    var groupId = ""
                    var artifactId = ""
                    var version = ""

                    publishing.publications {
                        val mavenPublication =
                            getByName(MAVEN_PUBLICATION_NAME) as MavenPublication
                        groupId = mavenPublication.groupId
                        artifactId = mavenPublication.artifactId
                        version = mavenPublication.version
                    }
                    publishing.repositories {
                        maven {
                            //url可能为null，虽然提示不会为null
                            PluginLogUtil.printlnInfoInScreen("$name url: ${url?.toString()}")
                        }
                    }
                    publishing.repositories.maven {
                        PluginLogUtil.printlnInfoInScreen(" ${name} url: ${url?.toString()}")
                    }
                    val fileNames = groupId.split(".")
                    val pathSb = StringBuilder()
                    pathSb.append(getPublishingExtensionRepositoriesPath(publishing))
                    fileNames.forEach {
                        pathSb.append(it)
                        pathSb.append(File.separatorChar)
                    }
//                    pathSb.append(artifactId)
//                    pathSb.append(File.separatorChar)
                    PluginLogUtil.printlnInfoInScreen("构建成功")
                    PluginLogUtil.printlnInfoInScreen("仓库地址：  $pathSb")
                    PluginLogUtil.printlnInfoInScreen("===================================================================")
                    PluginLogUtil.printlnInfoInScreen("")
                    PluginLogUtil.printlnInfoInScreen("implementation '$groupId:$artifactId:$version'")
                    PluginLogUtil.printlnInfoInScreen("")
                    PluginLogUtil.printlnInfoInScreen("==================================================================")
                    PluginLogUtil.printlnInfoInScreen("")
                    PluginLogUtil.printlnInfoInScreen("implementation (\"$groupId:$artifactId:$version\")")
                    PluginLogUtil.printlnInfoInScreen("")
                    PluginLogUtil.printlnInfoInScreen("==================================================================")
                    //提示成功信息
                } else {
                    //提示错误信息
                }
            } else {
                PluginLogUtil.printlnErrorInScreen("===============================下面是执行指令的输出结果===============================")
                PluginLogUtil.printlnErrorInScreen("")
                PluginLogUtil.printlnErrorInScreen(result)
                PluginLogUtil.printlnErrorInScreen("")
                PluginLogUtil.printlnErrorInScreen("==================================================================================")
                throw Exception("上传Maven仓库失败，请检查配置！")
            }
            PluginLogUtil.printlnDebugInScreen("$TAG executeTask finish ")
        }
    }


    /**
     * 上报服务器进行版本检查,这里直接模拟返回成功
     * */
    protected open fun checkPublishInfo(publishInfo: PublishInfoExtension): Boolean {
        return true
    }

    abstract fun getPublishingExtensionRepositoriesPath(publishing: PublishingExtension): String

    /**
     * 上报服务器进行版本更新操作,这里直接模拟返回成功
     * */
    private fun requestUploadVersion(): Boolean {
        return true
    }

    abstract fun fetchTaskName():String
}