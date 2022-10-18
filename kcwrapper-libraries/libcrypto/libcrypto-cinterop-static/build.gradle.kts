plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
//    linuxX64()
//    macosX64()
//    macosArm64()
    mingwX64 {
        val main by compilations.getting {
            val cinterop by cinterops.creating {
                defFile("cinterop.def")
            }
        }
    }
    sourceSets {
        commonMain {
            kotlin.srcDir(file("sources"))
            dependencies {
                api(project(":libcrypto:libcrypto-cinterop-api"))
            }
        }
    }
}
