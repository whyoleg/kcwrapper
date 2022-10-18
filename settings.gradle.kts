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

fun includeLibrary(libraryName: String, vararg submodules: String) {
    submodules.forEach {
        val projectName = "$libraryName-$it"
        val projectPath = "$libraryName:$projectName"
        include(projectPath)
        project(":$projectPath").projectDir = file("kcwrapper-libraries/$libraryName/$projectName")
    }
}

includeLibrary("libcrypto", "cinterop-api", "cinterop-dynamic", "cinterop-static", "cinterop-tests")

//includeLibrary("libssl")
//includeLibrary("libcrypto3")
//includeLibrary("libssl3")
//includeLibrary("libcurl")
