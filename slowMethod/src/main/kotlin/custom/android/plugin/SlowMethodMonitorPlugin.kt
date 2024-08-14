package custom.android.plugin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationParameters
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import custom.android.plugin.log.PluginLogUtil
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.util.TraceClassVisitor
import java.io.File
import java.io.PrintWriter


// 声明插件配置信息
open class AsmExtension {

    // 是否可用
    var enabled: Boolean = false

    var onClickListener: String? = null

}

class SlowMethodMonitorPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("================== AsmPlugin is Running =================")
        val extension = target.extensions.create("AsmConfig", AsmExtension::class.java)
        val androidComponents = target.extensions
            .getByType(AndroidComponentsExtension::class.java)

        androidComponents.onVariants { variant ->
            variant.instrumentation.transformClassesWith(
                ViewOnClickClassVisitorFactory::class.java,
                InstrumentationScope.ALL
            ) { it.enabled.set(extension.enabled) }
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
        }
//        target.afterEvaluate {
//            println("=====> 当前配置的参数：enabled=${extension.enabled},onClickListener=${extension.onClickListener}")
//            if (extension.enabled) {
//                println("====> started!")
//                ViewClickHandlerVisitor(Opcodes.ASM9)
//            }
//        }
    }

    interface ViewOnClickParams : InstrumentationParameters {

        @get:Input
        val enabled: Property<Boolean>

    }

    abstract class ViewOnClickClassVisitorFactory : AsmClassVisitorFactory<ViewOnClickParams> {

        override fun createClassVisitor(
            classContext: ClassContext,
            nextClassVisitor: ClassVisitor
        ): ClassVisitor {
            // 如果插件启用，则执行ClassVisitor操作
            return if (parameters.get().enabled.get()) {
                ViewClickHandlerVisitor(Opcodes.ASM9, nextClassVisitor)
            } else {
                TraceClassVisitor(nextClassVisitor, PrintWriter(File("trace_out")))
            }
        }

        // 这里判断需要对哪些类进行浏览，一般建议默认设置为：true
        override fun isInstrumentable(classData: ClassData): Boolean {
            PluginLogUtil.printlnDebugInScreen(
                "className: ${classData.className} "
            )
            return true
        }
        // classData.className.startsWith("com.xxx.analysis")

    }

}