import org.jetbrains.kotlin.gradle.dsl.*

fun KotlinMultiplatformExtension.nativeTargets() {
    linuxX64()
    mingwX64()
    macosX64()
    macosArm64()
    iosArm64()
    iosX64()
    iosSimulatorArm64()
}
