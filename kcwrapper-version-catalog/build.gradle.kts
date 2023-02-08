plugins {
    `version-catalog`
    id("buildx-publish")
}

description = "kcwrapper Gradle Version Catalog"

catalog {
    versionCatalog {
        //just a hint on version used by the library
        version("kotlin", libs.versions.kotlin.get())
        version("kcwrapper", version.toString())

        listOf("api", "dynamic", "static").forEach {
            library("libraries-libcrypto3-$it", "dev.whyoleg.kcwrapper", "libcrypto3-$it").versionRef("kcwrapper")
        }

        //TODO: name?
        plugin("kcwrapper", "dev.whyoleg.kcwrapper").versionRef("kcwrapper")
    }
}

publishing {
    publications {
        val versionCatalog by creating(MavenPublication::class) {
            from(components["versionCatalog"])
        }
    }
}
