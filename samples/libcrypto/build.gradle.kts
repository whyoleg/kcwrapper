plugins {
    kotlin("multiplatform") version "1.7.20"
}

kotlin {
    mingwX64 {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":libcrypto:libcrypto-cinterop-static"))
            }
        }
    }
}
