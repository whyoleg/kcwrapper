package dev.whyoleg.kcwrapper.gradle.plugin

import org.gradle.api.*
import org.gradle.api.tasks.*

class KCWrapperRootPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = target.run {
        check(this == rootProject)

        extensions.create(KCWrapperRootExtension.NAME, KCWrapperRootExtension::class.java, this)

        val buildDir = buildDir.resolve("kcwrapper")
        val opensslBuildDir = buildDir.resolve("openssl3")

        val zipFileTree = tasks.register(Openssl3PrebuiltDownloadTask.NAME, Openssl3PrebuiltDownloadTaskImpl::class.java) {
            it.src("https://github.com/whyoleg/openssl-builds/releases/download/3.0.7-alpha4/openssl3-all.zip")
            it.onlyIfModified(true)
            it.overwrite(false)
            it.dest(opensslBuildDir.resolve("prebuilt.zip"))
        }.map { zipTree(it.dest) }

        tasks.register(Openssl3PrebuiltSetupTask.NAME, Openssl3PrebuiltSetupTaskImpl::class.java) {
            it.from(zipFileTree)
            it.destinationDir = opensslBuildDir.resolve("prebuilt")
        }
    }
}

open class KCWrapperRootExtension(
    @Transient val rootProject: Project,
) {
    init {
        check(rootProject == rootProject.rootProject)
    }

    val openssl3PrebuiltSetupTaskProvider: TaskProvider<Openssl3PrebuiltSetupTask>
        get() = rootProject.tasks.named(Openssl3PrebuiltSetupTask.NAME, Openssl3PrebuiltSetupTask::class.java)

    companion object {
        const val NAME: String = "kcwrapper"
    }
}
