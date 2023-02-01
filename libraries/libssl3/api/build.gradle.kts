import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.tasks.*
import org.jetbrains.kotlin.konan.target.*

plugins {
    id("buildx-multiplatform-library")
}

val unzipPrebuiltOpenSSL3 = parent!!.tasks.named<Sync>("unzipPrebuiltOpenSSL3")

val libcryptoProject = evaluationDependsOn(":libraries:libcrypto3:libcrypto3-api")

tasks.withType<CInteropProcess>().configureEach {
    dependsOn(unzipPrebuiltOpenSSL3)
    dependsOn(libcryptoProject.tasks.named("commonize"))
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
                defFile("declarations.def")
                includeDirs(unzipPrebuiltOpenSSL3.map { it.destinationDir.resolve("$prebuiltName/include") })
            }
        }
    }
}
