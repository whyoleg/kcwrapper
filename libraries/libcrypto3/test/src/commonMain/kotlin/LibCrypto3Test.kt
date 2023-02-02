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

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
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
}


private const val hexCode = "0123456789ABCDEF"

private fun parseHexBinary(s: String): ByteArray {
    val len = s.length
    require(len % 2 == 0) { "HexBinary string must be even length" }
    val bytes = ByteArray(len / 2)
    var i = 0

    while (i < len) {
        val h = hexToInt(s[i])
        val l = hexToInt(s[i + 1])
        require(!(h == -1 || l == -1)) { "Invalid hex chars: ${s[i]}${s[i + 1]}" }

        bytes[i / 2] = ((h shl 4) + l).toByte()
        i += 2
    }

    return bytes
}

private fun hexToInt(ch: Char): Int = when (ch) {
    in '0'..'9' -> ch - '0'
    in 'A'..'F' -> ch - 'A' + 10
    in 'a'..'f' -> ch - 'a' + 10
    else        -> -1
}

private fun printHexBinary(data: ByteArray, lowerCase: Boolean = true): String {
    val r = StringBuilder(data.size * 2)
    for (b in data) {
        r.append(hexCode[b.toInt() shr 4 and 0xF])
        r.append(hexCode[b.toInt() and 0xF])
    }
    return if (lowerCase) r.toString().lowercase() else r.toString()
}
