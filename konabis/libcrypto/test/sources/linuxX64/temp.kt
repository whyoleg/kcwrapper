import konabis.libcrypto.*
import kotlinx.cinterop.*
import kotlin.test.*

@OptIn(ExperimentalUnsignedTypes::class)
class test2 {

    @Test
    fun test() {
        println(OPENSSL_VERSION_TEXT)
        val data = "Hello World".encodeToByteArray().asUByteArray()
        val hash = ByteArray(32).asUByteArray()
        val result = SHA256(data.refTo(0), data.size.convert(), hash.refTo(0))
        println(hash.asByteArray().decodeToString())
    }
}