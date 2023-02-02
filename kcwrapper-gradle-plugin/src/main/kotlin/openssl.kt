package dev.whyoleg.kcwrapper.gradle.plugin

import org.gradle.api.*
import org.jetbrains.kotlin.konan.target.*
import java.io.*

/*sealed*/ interface Openssl3PrebuiltSetupTask : Task {
    fun includeDir(target: Openssl3Target): File
    fun libDir(target: Openssl3Target): File

    companion object {
        const val NAME: String = "openssl3PrebuiltSetup"
    }
}

/*sealed*/ interface Openssl3PrebuiltDownloadTask : Task {
    companion object {
        const val NAME: String = "openssl3PrebuiltDownload"
    }
}

enum class Openssl3Target(val target: String) {
    IOS_DEVICE_ARM64("ios-device-arm64"),
    IOS_SIMULATOR_ARM64("ios-simulator-arm64"),
    IOS_SIMULATOR_X64("ios-simulator-x64"),
    LINUX_X64("linux-x64"),
    MACOS_ARM64("macos-arm64"),
    MACOS_X64("macos-x64"),
    MINGW_X64("mingw-x64"),
}

fun KonanTarget.toOpenssl3Target() = when (this) {
    KonanTarget.IOS_ARM64           -> Openssl3Target.IOS_DEVICE_ARM64
    KonanTarget.IOS_SIMULATOR_ARM64 -> Openssl3Target.IOS_SIMULATOR_ARM64
    KonanTarget.IOS_X64             -> Openssl3Target.IOS_SIMULATOR_X64
    KonanTarget.LINUX_X64           -> Openssl3Target.LINUX_X64
    KonanTarget.MACOS_ARM64         -> Openssl3Target.MACOS_ARM64
    KonanTarget.MACOS_X64           -> Openssl3Target.MACOS_X64
    KonanTarget.MINGW_X64           -> Openssl3Target.MINGW_X64
    else                            -> TODO("Unsupported target: $this")
}
