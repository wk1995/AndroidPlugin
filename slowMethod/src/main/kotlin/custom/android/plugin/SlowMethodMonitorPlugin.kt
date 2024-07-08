package custom.android.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

open class SlowMethodMonitorPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        println("SlowMethodMonitorPlugin apply")
    }
}