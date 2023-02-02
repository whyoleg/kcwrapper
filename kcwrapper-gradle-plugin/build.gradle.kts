import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("jvm") version "1.8.0"
    id("com.gradle.plugin-publish") version "1.0.0"
    id("com.github.ben-manes.versions") version "0.44.0"
}

gradlePlugin {
//    website.set("<substitute your project website>")
//    vcsUrl.set("<uri to project source repository>")
    plugins {
        val defaultPlugin by creating {
            id = "dev.whyoleg.kcwrapper"
            implementationClass = "dev.whyoleg.kcwrapper.gradle.plugin.KCWrapperPlugin"
//            displayName = "<short displayable name for plugin>"
//            description = "<human-readable description of what your plugin is about>"
        }
    }
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly(kotlin("gradle-plugin"))

    implementation("de.undercouch:gradle-download-task:5.3.0")
}

kotlin {
    jvmToolchain(8)

    target {
        compilations.all {
            @Suppress("DEPRECATION")
            compilerOptions.configure {
                languageVersion.set(KotlinVersion.KOTLIN_1_4)
                apiVersion.set(KotlinVersion.KOTLIN_1_4)
            }
        }
    }
}
