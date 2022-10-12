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
                defFile("libcrypto.def")
                includeDirs("/home/linuxbrew/.linuxbrew/Cellar/openssl@1.1/1.1.1q/include")
            }
        }
    }
}

//linkerOpts.linux = /home/linuxbrew/.linuxbrew/Cellar/openssl@1.1/1.1.1q/lib/libcrypto.so.1.1
//compilerOpts.linux = -I/home/linuxbrew/.linuxbrew/Cellar/openssl@1.1/1.1.1q/include

tasks.withType<KotlinNativeHostTest> {
    environment("LD_LIBRARY_PATH", "/home/linuxbrew/.linuxbrew/Cellar/openssl@1.1/1.1.1q/lib")
}
