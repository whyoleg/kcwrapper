package dev.whyoleg.kcwrapper.libcrypto3.test

import dev.whyoleg.kcwrapper.libcrypto3.cinterop.*
import kotlinx.cinterop.*
import kotlin.random.*
import kotlin.test.*

abstract class LibCrypto3Test {

    @Test
    fun testVersion() {
        assertEquals(3, OpenSSL_version(OPENSSL_VERSION_STRING)?.toKString()!!.first().digitToInt())
        assertEquals(3, OPENSSL_version_major().toInt())
    }

    @Test
    @OptIn(ExperimentalUnsignedTypes::class)
    fun testSha() {
        val md = EVP_MD_fetch(null, "SHA256", null)
        try {
            val context = EVP_MD_CTX_new()
            try {
                val data = "Hello World".encodeToByteArray()
                val digest = ByteArray(32)

                check(EVP_DigestInit(context, md) == 1)
                check(EVP_DigestUpdate(context, data.refTo(0), data.size.convert()) == 1)
                check(EVP_DigestFinal(context, digest.asUByteArray().refTo(0), null) == 1)

                assertEquals("a591a6d40bf420404a011733cfb7b190d62c65bf0bcda32b57b277d9ad9f146e", printHexBinary(digest))
            } finally {
                EVP_MD_CTX_free(context)
            }
        } finally {
            EVP_MD_free(md)
        }
    }

    @Test
    @OptIn(ExperimentalUnsignedTypes::class)
    fun testHmac(): Unit = memScoped {
        val dataInput = "Hi There".encodeToByteArray()
        val hashAlgorithm = "SHA256"
        val key = parseHexBinary("0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b")

        val mac = EVP_MAC_fetch(null, "HMAC", null)
        val context = EVP_MAC_CTX_new(mac)
        try {
            checkError(
                EVP_MAC_init(
                    ctx = context,
                    key = key.asUByteArray().refTo(0),
                    keylen = key.size.convert(),
                    params = OSSL_PARAM_array(
                        OSSL_PARAM_construct_utf8_string("digest".cstr.ptr, hashAlgorithm.cstr.ptr, 0)
                    )
                )
            )
            val signature = ByteArray(EVP_MAC_CTX_get_mac_size(context).convert())

            checkError(EVP_MAC_update(context, dataInput.fixEmpty().asUByteArray().refTo(0), dataInput.size.convert()))
            checkError(EVP_MAC_final(context, signature.asUByteArray().refTo(0), null, signature.size.convert()))

            assertEquals("b0344c61d8db38535ca8afceaf0bf12b881dc200c9833da726e9376c2e32cff7", printHexBinary(signature))
        } finally {
            EVP_MAC_CTX_free(context)
            EVP_MAC_free(mac)
        }
    }
}
