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
//        val main by compilations.getting {
//            val libcryptoStatic by cinterops.creating {
//                defFile("libcrypto.def")
//            }
//        }
        if (name == "linuxX64") {
            compilations.all {
                kotlinOptions {
                    freeCompilerArgs += listOf(
                        "-include-binary",
                        "/home/linuxbrew/.linuxbrew/Cellar/openssl@1.1/1.1.1q/lib/libcrypto.a",
                    )
                }
            }
        }
    }
}
