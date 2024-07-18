import custom.android.plugin.base.dependency.databaseRoom
import custom.android.plugin.base.dependency.DependencyItem

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
