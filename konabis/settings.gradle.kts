pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    includeBuild("../gradle/buildx") {
        name = "konabis-buildx"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "konabis"

//include("libcrypto:libcrypto-api")
//include("libcrypto:libcrypto-dynamic")
//include("libcrypto:libcrypto-static")


//include("libssl")
//include("libssl:libssl-static")
//
//include("libcrypto3")
//include("libcrypto3:libcrypto3-static")
//
//include("libssl3")
//include("libssl3:libssl3-static")
