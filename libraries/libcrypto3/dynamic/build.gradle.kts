import dev.whyoleg.kcwrapper.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    id("buildx-multiplatform-library")
    id("dev.whyoleg.kcwrapper")
}

kotlin {
    nativeDesktopTargets()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.libraries.libcrypto3.libcrypto3Api)
            }
        }
        val commonTest by getting {
            dependencies {
                api(projects.libraries.libcrypto3.libcrypto3Test)
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

val prebuiltSetup = kcwrapper.root.openssl3PrebuiltSetupTaskProvider

tasks.withType<KotlinNativeLink>().configureEach {
    dependsOn(prebuiltSetup)
    binary.linkerOpts("-L${prebuiltSetup.get().libDir(binary.target.konanTarget.toOpenssl3Target()).absolutePath}")
}

//tasks.withType<KotlinNativeTest>().configureEach {
//    dependsOn(prebuiltSetup)
//
//    val libraryPath = prebuiltSetup.get().libDir(HostManager.host.toOpenssl3Target()).absolutePath
//    when (HostManager.host) {
//        KonanTarget.LINUX_X64                          -> environment("LD_LIBRARY_PATH", libraryPath)
//        KonanTarget.MACOS_X64, KonanTarget.MACOS_ARM64 -> environment("DYLD_LIBRARY_PATH", libraryPath)
//        KonanTarget.MINGW_X64                          -> environment("PATH", libraryPath)
//        else                                           -> error("Unsupported host: ${HostManager.host}")
//    }
//}
