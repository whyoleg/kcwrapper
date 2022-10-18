pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    includeBuild("gradle/buildx") {
        name = "kcwrapper-buildx"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "kcwrapper"

//includeBuild("kcwrapper-gradle-plugin") //TODO: move to plugin management?

fun includeLibrary(name: String) {
    listOf("api", "dynamic", "static").forEach {
        include("kcwrapper-libraries:$name:$name-$it")
    }
}

includeLibrary("libcrypto")
//includeLibrary("libssl")
//includeLibrary("libcrypto3")
//includeLibrary("libssl3")
//includeLibrary("libcurl")
