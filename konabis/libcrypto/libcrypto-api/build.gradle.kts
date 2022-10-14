import org.jetbrains.kotlin.gradle.plugin.mpp.*

plugins {
    id("buildx-multiplatform")
    `maven-publish`
}

kotlin {
    targets.all {
        if (this !is KotlinNativeTarget) return@all
        val main by compilations.getting {
            val libcrypto by cinterops.creating {
                defFile("../libcrypto-interop/api.def")
                includeDirs("/home/linuxbrew/.linuxbrew/Cellar/openssl@1.1/1.1.1q/include")
            }
        }
    }
}
