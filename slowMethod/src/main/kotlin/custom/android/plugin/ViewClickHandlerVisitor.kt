package custom.android.plugin

import custom.android.plugin.log.PluginLogUtil
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 *
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2024/7/22
 *      desc   :
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * */
class ViewClickHandlerVisitor @JvmOverloads constructor(
    api: Int,
    classVisitor: ClassVisitor? = null
) : ClassVisitor(api, classVisitor) {

    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        PluginLogUtil.printlnDebugInScreen("【visit class】name=$name")
    }

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        PluginLogUtil.printlnDebugInScreen("====>方法信息： $access,$name,$descriptor,$signature,$exceptions")
        // 这里判断，我们检索的onClick方法是公开的且非静态的，参数只有View
        if (name == "onClick"
            && access.and(Opcodes.ACC_PUBLIC) == 1
            && access.and(Opcodes.ACC_STATIC) == 0
            && descriptor == "(Landroid/view/View;)V") {
            PluginLogUtil.printlnDebugInScreen("======> I found the setOnClickListener method!")
            val mv = cv.visitMethod(access, name, descriptor, signature, exceptions)
            return ViewClickMethodAdapter(Opcodes.ASM9, mv, access, name, descriptor)
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions)
    }

}