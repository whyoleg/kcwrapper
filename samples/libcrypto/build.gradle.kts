plugins {
    kotlin("multiplatform") version "1.7.20"
}

kotlin {
    linuxX64 {
        binaries {
            executable {
                entryPoint = "main"
                runTask!!.environment("LD_LIBRARY_PATH", "/home/linuxbrew/.linuxbrew/Cellar/openssl@1.1/1.1.1q/lib")
            }
        }
        compilations.all {
            kotlinOptions {
                freeCompilerArgs += listOf(
//                    "-linker-option", "/home/linuxbrew/.linuxbrew/Cellar/openssl@1.1/1.1.1q/lib/libcrypto.so",
//                    "-linker-option", "--allow-shlib-undefined"
                )
            }
        }
    }

    sourceSets {
        val linuxX64Main by getting {
            dependencies {
                implementation("dev.whyoleg.konabis:libcrypto-static:0.1.0")
            }
        }
    }
}
