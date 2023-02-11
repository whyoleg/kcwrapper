plugins {
    id("buildx-multiplatform")
}

kotlin {
    nativeTargets()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlin("test"))
                api(projects.libraries.libssl3.libssl3Api)
            }
        }
    }
}
