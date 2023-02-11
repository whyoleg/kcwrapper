import dev.whyoleg.kcwrapper.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.tasks.*
import org.jetbrains.kotlin.konan.target.*

plugins {
    id("buildx-multiplatform-library")
    id("dev.whyoleg.kcwrapper")
}

description = "kcwrapper libssl3 declarations"

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

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.libraries.libcrypto3.libcrypto3Api)
            }
        }
    }
}

val prebuiltSetup = kcwrapper.root.openssl3PrebuiltSetupTaskProvider

tasks.withType<CInteropProcess>().configureEach {
    dependsOn(prebuiltSetup)
    settings.includeDirs(prebuiltSetup.map { it.includeDir(konanTarget.toOpenssl3Target()) })
}
