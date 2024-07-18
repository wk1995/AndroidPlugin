package custom.android.plugin.push

import org.gradle.api.publish.PublishingExtension


/**
 * 如果不写成open，会报找不到这个类的错误
 * */
open class PublishLibraryLocalTask : BasePublishTask() {

    companion object {
        const val TAG = "PublishLibraryLocalTask"
    }

    override fun initPublishCommandLine() = ":publishToMavenLocal"

    override fun getPublishingExtensionRepositoriesPath(publishing: PublishingExtension): String {
        return publishing.repositories.mavenLocal().url.toString()
    }

    override fun fetchTaskName(): String = TAG
}