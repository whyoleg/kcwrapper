import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.tasks.*
import org.jetbrains.kotlin.konan.target.*

plugins {
    id("buildx-multiplatform-library")
}

kotlin {
    nativeTargets()

    targets.all {
        if (this !is KotlinNativeTarget) return@all

        val main by compilations.getting {
            val api by cinterops.creating {
                defFile("declarations.def")
            }
        }
    }
}

val unzipPrebuiltOpenSSL3 = parent!!.tasks.named<Sync>("unzipPrebuiltOpenSSL3")

tasks.withType<CInteropProcess>().configureEach {
    dependsOn(unzipPrebuiltOpenSSL3)

    val prebuiltName = when (konanTarget) {
        KonanTarget.IOS_ARM64           -> "ios-device-arm64"
        KonanTarget.IOS_SIMULATOR_ARM64 -> "ios-simulator-arm64"
        KonanTarget.IOS_X64             -> "ios-simulator-x64"
        KonanTarget.LINUX_X64           -> "linux-x64"
        KonanTarget.MACOS_ARM64         -> "macos-arm64"
        KonanTarget.MACOS_X64           -> "macos-x64"
        KonanTarget.MINGW_X64           -> "mingw-x64"
        else                            -> TODO("Unsupported target: $konanTarget")
    }
    settings.includeDirs(unzipPrebuiltOpenSSL3.map { it.destinationDir.resolve("$prebuiltName/include") })
}
