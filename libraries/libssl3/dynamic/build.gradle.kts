import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.targets.native.*
import org.jetbrains.kotlin.gradle.tasks.*
import org.jetbrains.kotlin.konan.target.*

plugins {
    id("buildx-multiplatform-library")
}

val unzipPrebuiltOpenSSL3 = parent!!.tasks.named<Sync>("unzipPrebuiltOpenSSL3")

kotlin {
    nativeTargets()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.libraries.libssl3.libssl3Api)
            }
        }
    }

    targets.all {
        return@all
        if (this !is KotlinNativeTarget) return@all

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

        val main by compilations.getting {
            val api by cinterops.creating {
                defFile("linking.def")
            }
        }

        if (this !is KotlinNativeTargetWithTests<*>) return@all

        testRuns.all {
            executionSource.binary.apply {
                linkTask.dependsOn(unzipPrebuiltOpenSSL3)
                linkerOpts("-L${unzipPrebuiltOpenSSL3.get().destinationDir.resolve("$prebuiltName/lib").absolutePath}")
            }
            this as AbstractKotlinNativeTestRun<*>

            //TODO: recheck this
            executionTask.configure {
                when (konanTarget) {
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
        }
    }
}
