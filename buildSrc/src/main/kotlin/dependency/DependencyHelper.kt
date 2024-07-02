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

