package custom.android.plugin

import custom.android.plugin.log.PluginLogUtil

object PicturePluginLogUtil {

    fun printlnDebugInScreen(msg: String) {
        PluginLogUtil.printlnDebugInScreen(msg)
    }

    fun printlnInfoInScreen(msg: String) {
        PluginLogUtil.printlnInfoInScreen(msg)
    }

    fun printlnErrorInScreen(msg: String) {
        PluginLogUtil.printlnErrorInScreen(msg)
    }
}