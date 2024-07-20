package custom.android.plugin

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.tinify.Source
import com.tinify.Tinify
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File


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
        val res = project.file("./src/main/res")
        PluginLogUtil.printlnDebugInScreen("res path: ${res.absolutePath}")
        Tinify.setKey("xxxx")
        res.listFiles()?.forEach {
            if (it.isDirectory && it.name.startsWith("drawable")) {
                //drawable folder
                try {
                    it.listFiles()?.forEach { pic ->
                        val picName = pic.name
                        if (picName.endsWith("jpg") || picName.endsWith("png") ||
                            picName.endsWith("webp") || picName.endsWith("jpeg")
                        ) {
                            //create temp folder
                            val tempFolder = File(it, "temp")
                            tempFolder.mkdirs()
                            val source: Source = Tinify.fromFile(pic.absolutePath)
                            val tempPic = File(tempFolder, pic.name)
                            source.toFile(tempPic.absolutePath)
                            pic.delete()
                            tempPic.renameTo(pic)

                        }
                    } ?: kotlin.run {
                        PluginLogUtil.printlnInfoInScreen("${it.name} folder do not have target picture")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    PluginLogUtil.printlnErrorInScreen("PicConvertToWebp error : ${e.message}")
                }
            }
        }

        /*  android?.sourceSets?.findByName("main")?.apply {
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
          }*/

    }
}