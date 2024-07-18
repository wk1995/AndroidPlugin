package custom.android.plugin.push

import custom.android.plugin.log.PluginLogUtil
import org.gradle.api.publish.PublishingExtension


open class PublishLibraryRemoteTask : BasePublishTask() {

    companion object {
        const val TAG = "PublishLibraryRemoteTask"
    }


    override fun initPublishCommandLine() =
        ":publish${MAVEN_PUBLICATION_NAME}PublicationToMavenRepository"


    override fun checkPublishInfo(publishInfo: PublishInfoExtension): Boolean {
        val version = publishInfo.version
        // 正则表达式解释：
        // \d+ 表示一个或多个数字（即非负整数）
        // (\.\d+)+ 表示`.`后跟一个或多个数字（即版本号的段），并且这样的段可以出现一次或多次
        // ^ 表示字符串开始
        // $ 表示字符串结束
        val regex = Regex("^\\d+(\\.\\d+)+$")
        val result = regex.matches(version)
        if (!result) {
            PluginLogUtil.printlnErrorInScreen("$publishInfo version not matches d.d.d")
        }
        return result
    }

    override fun getPublishingExtensionRepositoriesPath(publishing: PublishingExtension): String {
        return publishing.repositories.mavenCentral().url.toString()
    }

    override fun fetchTaskName(): String = TAG
}