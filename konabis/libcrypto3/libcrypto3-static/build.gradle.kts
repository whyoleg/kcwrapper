import org.jetbrains.kotlin.gradle.plugin.mpp.*

plugins {
    id("buildx-multiplatform")
    `maven-publish`
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":libcrypto3"))
            }
        }
    }
    targets.all {
        if (this !is KotlinNativeTarget) return@all
        if (name == "linuxX64") {
            compilations.all {
                kotlinOptions {
                    freeCompilerArgs += listOf(
                        "-include-binary",
                        "/home/linuxbrew/.linuxbrew/Cellar/openssl@3/3.0.6/lib/libcrypto.a"
                    )
                }
            }
        }
    }
}
