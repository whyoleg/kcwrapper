import dev.whyoleg.kcwrapper.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    id("buildx-multiplatform-library")
    id("dev.whyoleg.kcwrapper")
}

kotlin {
    nativeTargets()

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
            val static by cinterops.creating {
                defFile("linking.def")
            }
        }
    }
}

val prebuiltSetup = kcwrapper.root.openssl3PrebuiltSetupTaskProvider

tasks.withType<CInteropProcess>().configureEach {
    dependsOn(prebuiltSetup)
    settings.extraOpts(
        "-libraryPath",
        prebuiltSetup.get().dynamicLibDir(konanTarget.toOpenssl3Target()).absolutePath
    )
}
