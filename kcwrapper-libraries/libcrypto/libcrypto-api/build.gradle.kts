plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    mingwX64 {
        val main by compilations.getting {
            val api by cinterops.creating {
                defFile("api.def")
            }
        }
    }
//    linuxX64()
//    macosX64()
//    macosArm64()
}
