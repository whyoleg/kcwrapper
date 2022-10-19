import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
//    linuxX64()
//    macosX64()
//    macosArm64()
    mingwX64 {
        val main by compilations.getting {
            val bn by cinterops.creating {
                defFile("bn.def")
            }
            val bnCP = tasks.getByName<CInteropProcess>(bn.interopProcessingTaskName)
            val bio by cinterops.creating {
                defFile("bio.def")
                extraOpts("-library", bnCP.outputFileProvider.get())
            }
            tasks.named(bio.interopProcessingTaskName).get().dependsOn(bnCP)
            val bioCP = tasks.getByName<CInteropProcess>(bio.interopProcessingTaskName)
            val evp by cinterops.creating {
                defFile("evp.def")
                extraOpts("-library", bioCP.outputFileProvider.get())
                extraOpts("-library", bnCP.outputFileProvider.get())
            }
            tasks.named(evp.interopProcessingTaskName).get().dependsOn(bioCP)
            tasks.named(evp.interopProcessingTaskName).get().dependsOn(bnCP)
        }
    }
    sourceSets {
        commonMain {
            kotlin.srcDir(file("sources"))
        }
    }
}
