import org.jetbrains.kotlin.gradle.plugin.mpp.*

plugins {
    kotlin("multiplatform")
}

kotlin {
    targets.all {
        if (this is KotlinNativeTargetWithTests<*>) setupTests()

        compilations.all {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xrender-internal-diagnostic-names")
            }
        }
    }

    sourceSets.all {
        languageSettings {
            progressiveMode = true
        }
    }
}
