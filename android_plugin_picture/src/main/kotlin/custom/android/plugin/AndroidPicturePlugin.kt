package custom.android.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

open class AndroidPicturePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        PicturePluginLogUtil.printlnDebugInScreen("AndroidPicturePlugin apply")
        project.extensions.create(
            CompressImageInfoExtension.COMPRESS_IMAGE_INFO, CompressImageInfoExtension::class.java,
        )
        val currProjectName = project.displayName
        project.gradle.afterProject {
            if (currProjectName == displayName) {
                project.tasks.register(
                    CompressImageByTinyPngTask.TAG,
                    CompressImageByTinyPngTask::class.java
                )
            }
        }
    }
}