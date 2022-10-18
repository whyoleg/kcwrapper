import kcwrapper.libcrypto.interop.*
import kotlinx.cinterop.*

fun main() {
    println(OPENSSL_VERSION_TEXT)
    val data = "Hello World".encodeToByteArray().asUByteArray()
    val hash = ByteArray(32).asUByteArray()
    val result = SHA256(data.refTo(0), data.size.convert(), hash.refTo(0))
    println(hash.asByteArray().decodeToString())
    val result2 = EVP_EncryptInit(
        EVP_CIPHER_CTX_new(),
        EVP_aes_256_gcm(),
        null,
        null
    )

    println(result2)
}
