import org.jetbrains.kotlin.gradle.targets.native.tasks.*

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
//    linuxX64()
//    macosX64()
//    macosArm64()
    mingwX64("mingwX64Static")
    mingwX64("mingwX64Dynamic")
    sourceSets {
        commonTest {
            kotlin.srcDir(file("sources"))
            dependencies {
                api(project(":libcrypto:libcrypto-cinterop-api"))
            }
        }

        val mingwX64StaticTest by getting {
            dependencies {
                api(project(":libcrypto:libcrypto-cinterop-static"))
            }
        }
        val mingwX64DynamicTest by getting {
            dependencies {
                api(project(":libcrypto:libcrypto-cinterop-dynamic"))
            }
        }
    }
}

val mingwX64DynamicTest by tasks.getting(KotlinNativeHostTest::class) {
    environment("PATH", "C:/msys64/mingw64/bin")
}
