import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.targets.native.tasks.*

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    mingwX64 {
        val main by compilations.getting {
            val dynamic by cinterops.creating {
                defFile("dynamic.def")
            }
        }
    }

    sourceSets {
        val mingwX64Main by getting {
            dependencies {
                api(project(":kcwrapper-libraries:libcrypto:libcrypto-api"))
            }
        }
    }
//    linuxX64()
//    macosX64()
//    macosArm64()
}

val mingwX64Test by tasks.getting(KotlinNativeHostTest::class) {
    environment("PATH", "C:/msys64/mingw64/bin")
}
