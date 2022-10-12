import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.targets.native.tasks.*

plugins {
    id("buildx-multiplatform")
    `maven-publish`
}

kotlin {
    targets.all {
        if (this !is KotlinNativeTarget) return@all
        val main by compilations.getting {
            val libcrypto by cinterops.creating {
                defFile("libcrypto3.def")
                includeDirs("/home/linuxbrew/.linuxbrew/Cellar/openssl@3/3.0.6/include")
//                linkerOpts(
//                    "/home/linuxbrew/.linuxbrew/Cellar/openssl@3/3.0.6/lib/libcrypto.so.3",
//                    "--allow-shlib-undefined"
//                )
            }
        }
//        compilations.getByName("test") {
//            kotlinOptions {
//                freeCompilerArgs += listOf(
//                    "-linker-option", "/home/linuxbrew/.linuxbrew/Cellar/openssl@3/3.0.6/lib/libcrypto.so.3",
//                    "-linker-option", "--allow-shlib-undefined"
//                )
//            }
//        }
    }
}
//linkerOpts.linux=/home/linuxbrew/.linuxbrew/Cellar/openssl@3/3.0.6/lib/libcrypto.so.3
//tasks.withType<KotlinNativeHostTest> {
//    environment("LD_LIBRARY_PATH", "/home/linuxbrew/.linuxbrew/Cellar/openssl@3/3.0.6/lib")
//}
