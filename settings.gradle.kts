enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    includeBuild("gradle/plugins")
    includeBuild("kcwrapper-gradle-plugin")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

plugins {
    //this version can not be moved to version catalog, because it's in settings.gradle.kts
    id("com.gradle.enterprise") version "3.12.2"
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

rootProject.name = "kcwrapper"

fun includeLibrary(name: String) {
    listOf("api", "static", "dynamic", "test").forEach { submodule ->
        include("libraries:$name:$name-$submodule")
        project(":libraries:$name:$name-$submodule").projectDir = file("libraries/$name/$submodule")
    }
}

includeLibrary("libcrypto3")
