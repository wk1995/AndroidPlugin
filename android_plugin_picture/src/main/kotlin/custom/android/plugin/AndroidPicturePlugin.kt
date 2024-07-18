package custom.android.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

open class AndroidPicturePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("AndroidPicturePlugin apply")

        val currProjectName = project.displayName
        project.gradle.afterProject {
            if (currProjectName == displayName) {
                project.tasks.register(
                    PicConvertToWebpTask.TAG,
                    PicConvertToWebpTask::class.java
                )
            }
        }
    }
}