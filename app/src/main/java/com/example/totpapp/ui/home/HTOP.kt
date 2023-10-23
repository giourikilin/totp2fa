package com.example.totpapp.ui.home
import java.nio.ByteBuffer
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.math.pow


class HTOP(private val secret: ByteArray, private val counter: Long) {

    private val HMACALGORITHM = "HmacSHA1"

    fun getOTPcode(): String{
        val skey = SecretKeySpec(secret, HMACALGORITHM)
        val mac: Mac = try {
            Mac.getInstance(HMACALGORITHM)
        } catch ( e: NoSuchAlgorithmException) {
            throw RuntimeException("HmacSHA1 not found", e)
        }

        try {
            mac.init(skey)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Invalid key", e)
        }

        /*
        5.4.  Example of HOTP Computation for Digit = 6
              The following code example describes the extraction of a dynamic
              binary code given that hmac_result is a byte array with the HMAC-SHA-1 result:

              int offset   =  hmac_result[19] & 0xf ;
              int bin_code = (hmac_result[offset]  & 0x7f) << 24
                           | (hmac_result[offset+1] & 0xff) << 16
                           | (hmac_result[offset+2] & 0xff) <<  8
                           | (hmac_result[offset+3] & 0xff) ;

              SHA-1 HMAC Bytes (Example)
              https://datatracker.ietf.org/doc/html/rfc4226#section-1

              We treat the dynamic binary code as a 31-bit, unsigned, big-endian
              integer; the first byte is masked with a 0x7f.
              We then take this number modulo 1,000,000 (10^6) to generate the 6-digit HOTP value
        * */

        val counterBytes = ByteBuffer.allocate(8).putLong(counter).array()
        val hashedCounter = try {
            mac.doFinal(counterBytes)
        } catch (e: IllegalStateException) {
            throw RuntimeException("MAC can not be calculated check input",e)
        }
        val offset = hashedCounter[hashedCounter.size - 1].toInt() and 0x0F
        val bin_code = ((hashedCounter[offset].toInt() and 0x7F) shl 24
                or (hashedCounter[offset + 1].toInt() and 0xFF) shl 16
                or (hashedCounter[offset + 2].toInt() and 0xFF) shl 8
                or (hashedCounter[offset + 3].toInt() and 0xFF))
        val generatedOTP = bin_code % 10.0.pow(6.0).toInt()

        return generatedOTP.toString()
    }


}