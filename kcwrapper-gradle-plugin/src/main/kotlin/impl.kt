package dev.whyoleg.kcwrapper.gradle.plugin

import de.undercouch.gradle.tasks.download.*
import org.gradle.api.tasks.*
import java.io.*

abstract class Openssl3PrebuiltSetupTaskImpl : Openssl3PrebuiltSetupTask, Sync() {
    override fun includeDir(target: Openssl3Target): File = destinationDir.resolve(target.target).resolve("include")
    override fun libDir(target: Openssl3Target): File = destinationDir.resolve(target.target).resolve("lib")
}

abstract class Openssl3PrebuiltDownloadTaskImpl : Openssl3PrebuiltDownloadTask, Download()
