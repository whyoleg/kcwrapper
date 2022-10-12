plugins {
    org.jetbrains.kotlin.multiplatform
}

kotlin {
    explicitApi()

    linuxX64()
    macosX64()
    macosArm64()

    sourceSets {
        all {
            languageSettings {
                progressiveMode = true
            }
            val (targetName, compilationName) = name.run {
                val index = indexOfLast { it.isUpperCase() }
                take(index) to drop(index).toLowerCase()
            }
            kotlin.dir("$compilationName/sources/$targetName")
            resources.dir("$compilationName/resources/$targetName")
        }
    }
}
