import dev.whyoleg.kcwrapper.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.tasks.*
import org.jetbrains.kotlin.konan.target.*

plugins {
    id("buildx-multiplatform-library")
    id("dev.whyoleg.kcwrapper")
}

description = "kcwrapper libssl3 with dynamic linking"

kotlin {
    nativeDesktopTargets()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.libraries.libssl3.libssl3Api)
            }
        }
        val commonTest by getting {
            dependencies {
                api(projects.libraries.libssl3.libssl3Test)
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
