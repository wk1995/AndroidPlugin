package custom.android.plugin

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.api.AndroidSourceDirectorySet
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class PicConvertToWebpTask : DefaultTask() {

    companion object {
        const val TAG = "PicConvertToWebp"
    }

    init {
        group = "customPlugin"
    }

    @TaskAction
    fun doTask() {
        val applicationAndroid = project.extensions.findByType(BaseAppModuleExtension::class.java)
        val libraryAndroid = project.extensions.findByType(LibraryExtension::class.java)
        val android = applicationAndroid ?: libraryAndroid ?: kotlin.run {
            PluginLogUtil.printlnErrorInScreen("android is null")
            null
        }
        android?.sourceSets?.findByName("main")?.apply {
            PluginLogUtil.printlnDebugInScreen("this AndroidSourceSet is : ${this.java.javaClass.name}")
            if (this is AndroidSourceDirectorySet) {
                this.srcDirs.let { files ->
                    PluginLogUtil.printlnDebugInScreen("res srcDirs size : ${files.size}")
                    files.forEach { file ->
                        PluginLogUtil.printlnDebugInScreen("res srcDir: ${file.absolutePath}/${file.name}")
                    }
                }
            } else {
                PluginLogUtil.printlnErrorInScreen("is not AndroidSourceDirectorySet")
            }

        } ?: kotlin.run {
            PluginLogUtil.printlnErrorInScreen("main sourceSet is not exist")
        }

    }
}