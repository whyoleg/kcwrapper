# 0.1.0 Initial release

* [ ] apply some plugins (like in cryptograph-kotlin) - bom, VC, dokka (?), etc
* [ ] GitHub Snapshots
* [ ] Maven Central
* [ ] Release at the same time as cryptograph-kotlin

# 0.2.0/0.3.0 libssl/libcurl/libtd (require kotlin 1.8.20)

* [ ] need https://youtrack.jetbrains.com/issue/KT-51517/C-Interop-Commonizer-Fails-On-Classifier-That-Doesnt-Exist
* [ ] libssl support dependent on libcrypto
* [ ] libcurl support dependent on libssl
* [ ] libtd support dependent on libssl

# x.y.z plans

* [ ] libcrypto/libssl build for JVM and JS/WASM with minimal set of declarations (check skiko)
* [ ] better dynamic testing support
* [ ] conan integration
    * [ ] step 1: integrate conan into gradle plugin to build library for current OS
    * [ ] step 2: build toolchain for cross compile on macOS
    * [ ] step 3: build toolchain for cross compile on linux (may be windows)
* [ ] vcpkg integration

# Notes

* Gradle plugin to setup cinterop with static/dynamic linking of C libraries
* Gradle plugin to setup multiplatform cinterop/jvm(jni/jna/jextract)/js(ffi?) wrappers for C libraries
* Gradle plugin to easy use libraries and auto setup for test sourcesets

