package custom.android.plugin.log

object PluginLogUtil {
    private const val DEFAULT_COLOR_ERROR = "\u001B[31m"
    private const val DEFAULT_COLOR_SUCCESS = "\u001B[32m"
    private const val resetColor = "\u001B[0m"
    private const val maxLineLength = 200 // 适当的行长度
    /**
     * 打印到屏幕上
     * */
    private fun printlnInScreen(msg: String, colorString: String) {
        val segments = msg.chunked(maxLineLength)
        segments.forEach { segment ->
            println("$colorString$segment")
        }
    }

    fun printlnDebugInScreen(msg: String) {
        printlnInScreen(msg, resetColor)
    }



    fun printlnInfoInScreen(msg: String) {
        printlnInScreen(msg, DEFAULT_COLOR_SUCCESS)
    }

    fun printlnErrorInScreen(msg: String) {
        printlnInScreen(msg, DEFAULT_COLOR_ERROR)
    }
}