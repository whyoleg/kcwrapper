import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.targets.native.tasks.*

plugins {
    id("buildx-multiplatform")
    `maven-publish`
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":libcrypto:libcrypto-api"))
            }
        }
    }
    targets.all {
        if (this !is KotlinNativeTarget) return@all
        val main by compilations.getting {
            val libcrypto by cinterops.creating {
                defFile("../libcrypto-interop/dynamic.def")
            }
        }
    }
}

tasks.withType<KotlinNativeHostTest> {
    environment("LD_LIBRARY_PATH", "/home/linuxbrew/.linuxbrew/Cellar/openssl@1.1/1.1.1q/lib")
}
