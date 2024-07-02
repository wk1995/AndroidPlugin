package dependency

import dependency.DependencyType.DEPENDENCY_TYPE_IMPLEMENTATION
import org.gradle.api.artifacts.dsl.DependencyHandler


/**
 * @param configurationName see [DependencyType]
 * */
fun DependencyHandler.appcompat(configurationName: String = DEPENDENCY_TYPE_IMPLEMENTATION) {
    add(configurationName, DependencyItem.appcompat)
    add(configurationName, DependencyItem.supportV4)
    add(configurationName, DependencyItem.coreKtx)
    add(configurationName, DependencyItem.activityKtx)
    add(configurationName, DependencyItem.fragmentKtx)
    add(configurationName, DependencyItem.multidex)
}

/**
 * @param configurationName see [DependencyType]
 * */
fun DependencyHandler.affectiveSdk(configurationName: String = DEPENDENCY_TYPE_IMPLEMENTATION) {
    add(configurationName, DependencyItem.affectiveSdkOfflineFlowTime)
    add(configurationName, DependencyItem.affectiveSdkApi)
}

/**
 * 新建module 与app 默认依赖
 * */
fun DependencyHandler.initDependencies() {
    add(DEPENDENCY_TYPE_IMPLEMENTATION, DependencyItem.coreKtx)
    add(DEPENDENCY_TYPE_IMPLEMENTATION, DependencyItem.appcompat)
    add(DEPENDENCY_TYPE_IMPLEMENTATION, DependencyItem.material)
    add(DEPENDENCY_TYPE_IMPLEMENTATION, DependencyItem.constraintlayout)
    add(DependencyType.DEPENDENCY_TYPE_TEST_IMPLEMENTATION, DependencyItem.junit_junit)
    add(DependencyType.DEPENDENCY_TYPE_ANDROID_TEST_IMPLEMENTATION, DependencyItem.ext_junit)
    add(
        DependencyType.DEPENDENCY_TYPE_ANDROID_TEST_IMPLEMENTATION,
        DependencyItem.espresso_espresso_core
    )
}

