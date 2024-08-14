

先在项目的根build.gradle| build.gradle.kts 中引用插件

```
dependencies {
    classpath("custom.android.plugin:baseBuild:latest.release")
}
```

### application&#x20;

只需要在Android 中定义namespace，applicationId，versionCode，versionName

    plugins {
        id("com.android.application")
        id("android.plugin.baseBuild")
    }
    
    android {
        namespace = "com.wk.plugin"
        defaultConfig {
            applicationId = "com.wk.plugin"
            versionCode = 1
            versionName = "2.0.0"
        }
    }
    
    dependencies {
    	databaseRoom()//依赖room数据库 组
    	implementation(DependencyItem.recyclerView)//依赖单个库
    }

所有的依赖都在DependencyItem这个类中定义，可以自定义

所有的依赖组都放在DependencyHelper.kt文件里，可以自定义，但需要是DependencyHandler的扩展方法

### library

只需要在Android 中定义namespace

```

plugins {
    id("com.android.library")
    id("android.plugin.baseBuild")
}

PublishInfo{
    groupId = "custom.android.plugin"
    artifactId = "library"
    version = "0.0.1"
}

android {
    namespace = "cn.android.mylibrary"

}

dependencies {
    databaseRoom()//依赖room数据库 组
    implementation(DependencyItem.recyclerView)//依赖单个库
}
```

### Plugin&#x20;

```

plugins {
    `kotlin-dsl`
    id("android.plugin.baseBuild")
}

PublishInfo {
    groupId = "custom.android.plugin"
    artifactId = "monitor-slowMethod"
    version = "0.0.4"
    pluginId = "plugin.android.monitor.slow.method"//插件名
    implementationClass = "custom.android.plugin.SlowMethodMonitorPlugin"
}
```
运行 library or plugin Android studio gradle工具中的  customplugin 中的task 就能打出aar|jar

