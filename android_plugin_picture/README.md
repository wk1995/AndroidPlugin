

先在项目的根build.gradle| build.gradle.kts 中引用插件

```
dependencies {
    classpath("custom.android.plugin:picture:latest.release")
}
```

在module 的build.gradle 或者 build.gradle.kts中引用plugin.android.picture 插件

在根目录下的local.properties 文件中定义tinyPngApiKey值

```
tinyPngApiKey=xxxxxx
```

若没有tinypng  api key 则需要前往 https://tinypng.com/developers 申请 api key

运行 CompressImageByTinyPng task

