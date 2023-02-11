package dev.whyoleg.kcwrapper.libssl3.test

import dev.whyoleg.kcwrapper.libcrypto3.cinterop.*
import dev.whyoleg.kcwrapper.libssl3.cinterop.*
import kotlinx.cinterop.*
import kotlin.random.*
import kotlin.test.*

abstract class LibSsl3Test {

    @Test
    fun testVersion() {
        assertEquals(3, OpenSSL_version(OPENSSL_VERSION_STRING)?.toKString()!!.first().digitToInt())
        assertEquals(3, OPENSSL_version_major().toInt())
    }

}
