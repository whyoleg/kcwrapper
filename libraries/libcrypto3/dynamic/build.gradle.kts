import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.targets.native.*
import org.jetbrains.kotlin.gradle.targets.native.tasks.*
import org.jetbrains.kotlin.gradle.tasks.*
import org.jetbrains.kotlin.konan.target.*

plugins {
    id("buildx-multiplatform-library")
}

kotlin {
    nativeTargets()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.libraries.libcrypto3.libcrypto3Api)
            }
        }
    }

    targets.all {
        if (this !is KotlinNativeTarget) return@all

        val main by compilations.getting {
            val api by cinterops.creating {
                defFile("linking.def")
            }
        }
    }
}

val unzipPrebuiltOpenSSL3 = parent!!.tasks.named<Sync>("unzipPrebuiltOpenSSL3")

tasks.withType<KotlinNativeLink>().configureEach {
    dependsOn(unzipPrebuiltOpenSSL3)

    val prebuiltName = when (val konanTarget = binary.target.konanTarget) {
        KonanTarget.IOS_ARM64           -> "ios-device-arm64"
        KonanTarget.IOS_SIMULATOR_ARM64 -> "ios-simulator-arm64"
        KonanTarget.IOS_X64             -> "ios-simulator-x64"
        KonanTarget.LINUX_X64           -> "linux-x64"
        KonanTarget.MACOS_ARM64         -> "macos-arm64"
        KonanTarget.MACOS_X64           -> "macos-x64"
        KonanTarget.MINGW_X64           -> "mingw-x64"
        else                            -> TODO("Unsupported target: $konanTarget")
    }

    binary.linkerOpts("-L${unzipPrebuiltOpenSSL3.get().destinationDir.resolve("$prebuiltName/lib").absolutePath}")
}

tasks.withType<KotlinNativeTest>().configureEach {
    dependsOn(unzipPrebuiltOpenSSL3)

    val prebuiltName = when (val konanTarget = HostManager.host) {
        KonanTarget.IOS_ARM64           -> "ios-device-arm64"
        KonanTarget.IOS_SIMULATOR_ARM64 -> "ios-simulator-arm64"
        KonanTarget.IOS_X64             -> "ios-simulator-x64"
        KonanTarget.LINUX_X64           -> "linux-x64"
        KonanTarget.MACOS_ARM64         -> "macos-arm64"
        KonanTarget.MACOS_X64           -> "macos-x64"
        KonanTarget.MINGW_X64           -> "mingw-x64"
        else                            -> TODO("Unsupported target: $konanTarget")
    }

    //TODO?
    when (HostManager.host) {
        KonanTarget.LINUX_X64 -> {
            environment(
                "LD_LIBRARY_PATH",
                unzipPrebuiltOpenSSL3.get().destinationDir.resolve("$prebuiltName/lib").absolutePath
            )
        }
        KonanTarget.MACOS_X64 -> {
            environment(
                "DYLD_LIBRARY_PATH",
                unzipPrebuiltOpenSSL3.get().destinationDir.resolve("$prebuiltName/lib").absolutePath
            )
        }
        else                  -> {
            //do nothing
        }
    }
}
