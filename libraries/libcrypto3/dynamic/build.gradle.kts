import dev.whyoleg.kcwrapper.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.targets.native.tasks.*
import org.jetbrains.kotlin.gradle.tasks.*
import org.jetbrains.kotlin.konan.target.*

plugins {
    id("buildx-multiplatform-library")
    id("dev.whyoleg.kcwrapper")
}

description = "kcwrapper libcrypto3 with dynamic linking"

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
            val dynamic by cinterops.creating {
                defFile("linking.def")
            }
        }
    }
}

if (HostManager.host.family == Family.LINUX) {
    val target = HostManager.host.toOpenssl3Target()
    val prebuiltSetup = kcwrapper.root.openssl3PrebuiltSetupTaskProvider

    tasks.withType<KotlinNativeLink>().configureEach {
        dependsOn(prebuiltSetup)
        binary.linkerOpts("-L${prebuiltSetup.get().dynamicLibDir(target).absolutePath}")
    }
//    tasks.withType<KotlinNativeTest>().configureEach {
//        dependsOn(prebuiltSetup)
//        val libraryPath = prebuiltSetup.get().dynamicLibDir(target).absolutePath
//        environment("LD_LIBRARY_PATH", libraryPath)
//    }
}
