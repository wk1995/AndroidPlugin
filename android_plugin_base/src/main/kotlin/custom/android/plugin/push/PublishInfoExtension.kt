package custom.android.plugin.push

open class PublishInfoExtension() {

    companion object {
        const val EXTENSION_PUBLISH_INFO_NAME = "PublishInfo"
    }

    constructor(groupId: String, artifactId: String, version: String) : this() {
        this.groupId = groupId
        this.artifactId = artifactId
        this.version = version
    }

    constructor(
        groupId: String,
        artifactId: String,
        version: String,
        pluginId: String,
        implementationClass: String
    ) : this() {
        this.groupId = groupId
        this.artifactId = artifactId
        this.version = version
        this.pluginId = pluginId
        this.implementationClass = implementationClass
    }

    constructor(
        groupId: String,
        artifactId: String,
        version: String,
        pluginId: String,
        implementationClass: String,
        publishUrl: String,
        publishUserName: String,
        publishPassword: String
    ) : this() {
        this.groupId = groupId
        this.artifactId = artifactId
        this.version = version
        this.pluginId = pluginId
        this.implementationClass = implementationClass
        this.publishUrl = publishUrl
        this.publishUserName = publishUserName
        this.publishPassword = publishPassword
    }


    /**
     * 包名
     */
    var groupId = ""

    /**
     * 项目名
     */
    var artifactId = ""

    /**
     * 版本号
     */
    var version = ""

    var pluginId = ""

    var implementationClass = ""

    private var publishUrl: String = ""

    private var publishUserName: String = ""
    private var publishPassword: String = ""

    open fun getPublishUrl(): String {
        return publishUrl
    }

    open fun getPublishUserName(): String {
        return publishUserName
    }

    open fun getPublishPassword(): String {
        return publishPassword
    }

    open fun setPublishUrl(publishUrl: String) {
        this.publishUrl = publishUrl
    }

    open fun setPublishUserName(publishUserName: String) {
        this.publishUserName = publishUserName
    }

    open fun setPublishPassword(publishPassword: String) {
        this.publishPassword = publishPassword
    }

}