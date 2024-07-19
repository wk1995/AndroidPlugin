package custom.android.plugin.base

import org.gradle.api.plugins.PluginContainer

object ProjectHelper {

     fun isApp(container: PluginContainer): Boolean {
        return container.hasPlugin("com.android.application")
    }

     fun isPlugin(container: PluginContainer): Boolean {
        return container.hasPlugin("org.gradle.kotlin.kotlin-dsl")
                || container.hasPlugin("groovy")
    }

     fun isLibrary(container: PluginContainer) =
        container.hasPlugin("com.android.library")
}