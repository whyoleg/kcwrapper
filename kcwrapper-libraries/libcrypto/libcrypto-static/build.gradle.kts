import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    mingwX64 {
        val main by compilations.getting {
            val static by cinterops.creating {
                defFile("static.def")
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
