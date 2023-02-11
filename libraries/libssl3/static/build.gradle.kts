import dev.whyoleg.kcwrapper.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.tasks.*
import org.jetbrains.kotlin.konan.target.*

plugins {
    id("buildx-multiplatform-library")
    id("dev.whyoleg.kcwrapper")
}

description = "kcwrapper libssl3 with static linking"

kotlin {
    nativeTargets()

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
        prebuiltSetup.get().staticLibDir(konanTarget.toOpenssl3Target()).absolutePath
    )
}
