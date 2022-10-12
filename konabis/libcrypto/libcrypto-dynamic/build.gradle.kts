import org.jetbrains.kotlin.gradle.plugin.mpp.*

plugins {
    id("buildx-multiplatform")
    `maven-publish`
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":libcrypto"))
            }
        }
    }
    targets.all {
        if (this !is KotlinNativeTarget) return@all
        val main by compilations.getting {
            val libcryptoDynamic by cinterops.creating {
                defFile("libcrypto.def")
            }
        }

//        if (name == "linuxX64") {
//            val main by compilations.getting {
//                val libcrypto by cinterops.creating {
//                    defFile("libcrypto.def")
//                }
//            }
//
////            compilations.all {
////                kotlinOptions {
////                    freeCompilerArgs += listOf(
////                        "-library",
////                        "/home/linuxbrew/.linuxbrew/Cellar/openssl@1.1/1.1.1q/lib/libcrypto.so",
////                    )
////                }
////            }
//        }
    }
}
