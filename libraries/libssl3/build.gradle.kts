import de.undercouch.gradle.tasks.download.*

plugins {
    id("de.undercouch.download")
}

val downloadPrebuiltOpenSSL3 by tasks.registering(Download::class) {
    src(libs.versions.openssl3.map { "https://github.com/whyoleg/openssl-builds/releases/download/$it/openssl3-all.zip" })
    onlyIfModified(true)
    overwrite(false)
    dest(buildDir.resolve("openssl3-all.zip"))
}

val unzipPrebuiltOpenSSL3 by tasks.registering(Sync::class) {
    from(downloadPrebuiltOpenSSL3.map { zipTree(it.dest) })
    into(buildDir.resolve("openssl3-prebuilt"))
}
