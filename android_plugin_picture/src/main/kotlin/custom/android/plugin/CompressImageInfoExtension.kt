package custom.android.plugin

/**
 *
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2024/7/20
 *      desc   :
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * */
open class CompressImageInfoExtension(
    val resSrcs: List<String> = emptyList(),
    val toolsKey: String = ""
) {
    companion object {
        const val COMPRESS_IMAGE_INFO = "CompressImageInfo"
    }
}
