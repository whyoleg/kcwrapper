package dev.whyoleg.kcwrapper.gradle.plugin

import org.gradle.api.*

class KCWrapperPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = target.run {
        rootProject.plugins.apply(KCWrapperRootPlugin::class.java)
        extensions.create(KCWrapperExtension.NAME, KCWrapperExtension::class.java, this)
    }
}

abstract class KCWrapperExtension(
    @Transient val project: Project,
) {
    val root: KCWrapperRootExtension
        get() = project.rootProject.extensions.getByName(KCWrapperRootExtension.NAME) as KCWrapperRootExtension

    companion object {
        const val NAME: String = "kcwrapper"
    }
}
