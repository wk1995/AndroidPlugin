package custom.android.plugin

import custom.android.plugin.log.PluginLogUtil
import org.objectweb.asm.Handle
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

/**
 *
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2024/7/22
 *      desc   :
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * */
class ViewClickMethodAdapter(api: Int, methodVisitor: MethodVisitor?, access: Int, name: String?, descriptor: String?) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {


    override fun onMethodEnter() {
        super.onMethodEnter()

    }

    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
        //方法后面追加2333333333333333333输出
        run {
           mv?.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
           mv?.visitLdcInsn("233333333333333333333333333")
           mv?.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
        }
        /// 调用一个类，输出Click事件的接收
        run {
           mv?.visitVarInsn(Opcodes.ALOAD, 1)
           mv?.visitMethodInsn(Opcodes.INVOKESTATIC, "com/xxx/analysis/AnalysisUtils", "onReceivedClickEvent", "(Landroid/view/View;)V", false);
        }
       mv?.visitMaxs(1, 1) //这里可能有写法错误
       mv?.visitEnd()
    }

    override fun visitInvokeDynamicInsn(name: String?, descriptor: String?, bootstrapMethodHandle: Handle?, vararg bootstrapMethodArguments: Any?) {
        PluginLogUtil.printlnDebugInScreen("===================================> lambda: $name, $descriptor, $bootstrapMethodHandle, $bootstrapMethodArguments")
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, *bootstrapMethodArguments)
    }

}