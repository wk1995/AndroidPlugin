package custom.android.plugin

import com.tinify.Source
import com.tinify.Tinify
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.Properties


open class CompressImageByTinyPngTask : DefaultTask() {

    companion object {
        const val TAG = "CompressImageByTinyPng"
    }

    init {
        group = "customPlugin"
    }

    @TaskAction
    fun doTask() {
        val properties = Properties()// local.properties file in the root director
        properties.load(project.rootProject.file("local.properties").inputStream())
        PicturePluginLogUtil.printlnDebugInScreen("properties: $properties")
        var tinyPngApiKey = properties.getProperty("tinyPngApiKey", "")
        val compressImageInfo =
            project.extensions.findByType(CompressImageInfoExtension::class.java)
                ?: CompressImageInfoExtension()

        if (compressImageInfo.toolsKey.isNotEmpty()) {
            tinyPngApiKey = compressImageInfo.toolsKey
        }
        if (tinyPngApiKey.isEmpty()) {
            PicturePluginLogUtil.printlnErrorInScreen(
                "tinyPngApiKey is empty. " +
                        "Please set the \'tinyPngApiKey\' value in the \"local. properties\" file of the rootProject " +
                        "or define the CompressImageInfo \' toolsKey\' in the module gradle. " +
                        "If there is no API key, please go to https://tinypng.com/developers apply "
            )
            return
        }
        Tinify.setKey(tinyPngApiKey)
        var imagePaths = compressImageInfo.resSrcs
        if (imagePaths.isEmpty()) {
            PicturePluginLogUtil.printlnDebugInScreen("use default image src : \"./src/main/res\" ")
            imagePaths = listOf("./src/main/res")
        }
        val startTime = System.currentTimeMillis()
        PicturePluginLogUtil.printlnDebugInScreen("start compress: $startTime ")
        imagePaths.forEach { resPath ->
            val res = project.file(resPath)
            PicturePluginLogUtil.printlnDebugInScreen("res path: ${res.absolutePath}")
            res.listFiles()?.forEach { drawableFolder ->
                //is drawable folder
                if (drawableFolder.isDirectory && drawableFolder.name.startsWith("drawable")) {
                    try {
                        var tempFolder: File? = null
                        drawableFolder.listFiles()?.forEach { pic ->
                            val picName = pic.name
                            if (picName.endsWith("jpg") || picName.endsWith("png") ||
                                picName.endsWith("webp") || picName.endsWith("jpeg")
                            ) {
                                //create temp folder
                                tempFolder = File(drawableFolder, "temp")
                                tempFolder?.apply {
                                    mkdirs()
                                    val pre = pic.length()
                                    PicturePluginLogUtil.printlnDebugInScreen("compress before size $pre")
                                    val source: Source = Tinify.fromFile(pic.absolutePath)
                                    val tempPic = File(this, pic.name)
                                    source.toFile(tempPic.absolutePath)
                                    val tempLength = tempPic.length()
                                    pic.delete()
                                    tempPic.renameTo(pic)
                                    val compressRate = (pre - tempLength) * 1f / pre
                                    PicturePluginLogUtil.printlnDebugInScreen("picture $picName compress after size $tempLength  compressRate ${compressRate * 100}%")
                                }

                            }
                        } ?: kotlin.run {
                            PicturePluginLogUtil.printlnInfoInScreen("${drawableFolder.name} folder do not have target picture")
                        }
                        tempFolder?.delete()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        PicturePluginLogUtil.printlnErrorInScreen("PicConvertToWebp error : ${e.message}")
                    }
                }
            }
        }
        PicturePluginLogUtil.printlnDebugInScreen("compress time consuming ${System.currentTimeMillis() - startTime}ms ")

        /*  val applicationAndroid = project.extensions.findByType(BaseAppModuleExtension::class.java)
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
          }*/

    }
}