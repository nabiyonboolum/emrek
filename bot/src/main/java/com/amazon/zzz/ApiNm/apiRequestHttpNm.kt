package com.amazon.zzz.ApiNm

import com.amazon.zzz.ClipherNm.AesCryptorNm
import com.amazon.zzz.Utilsss.utUtils
import com.amazon.zzz.constNm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object apiRequestHttpNm {

    suspend fun doInBackground(url: String?, parametr: String?): String {
        var resultString = ""
        withContext(Dispatchers.IO) {
            try {
                val myURL = url
                var data: ByteArray?
                val `is`: InputStream?
                try {
                    data = parametr?.toByteArray(constNm.utf) ?: byteArrayOf()

                    val url = URL(myURL)
                    val conn = url.openConnection() as HttpURLConnection
                    conn.setRequestProperty("User-Agent", constNm.мозила)
                    conn.requestMethod = "POST"
                    conn.doOutput = true
                    conn.doInput = true
                    conn.setRequestProperty("Content-Length", (data.size).toString())

                    val os = conn.outputStream
                    os.write(data, 0, data.size)
                    os.flush()
                    os.close()

                    conn.connect()
                    val responseCode = conn.responseCode
                    val baos = ByteArrayOutputStream()
                    if (responseCode == 200) {
                        `is` = conn.inputStream
                        val buffer = ByteArray(8192)
                        var bytesRead: Int
                        while (`is`.read(buffer).also { bytesRead = it } != -1) {
                            baos.write(buffer, 0, bytesRead)
                        }
                        data = baos.toByteArray()
                        resultString = String(data, constNm.utf)
                    }
                } catch (e: MalformedURLException) {
                    utUtils.Log("api", e.localizedMessage)
                } catch (e: ConnectException) {
                    utUtils.Log("api", e.localizedMessage)
                } catch (e: IOException) {
                    utUtils.Log("api", e.localizedMessage)
                }
            } catch (e: Exception) {
                utUtils.Log("api", e.localizedMessage)
            }
        }
        return resultString
    }

    suspend fun sendRequest(url: String?, parametr: String?): String {
        val param = AesCryptorNm.encrypt(parametr ?: "", constNm.k)
        val out = doInBackground(url, param)
        return try {
            AesCryptorNm.decrypt(out, constNm.k)
        } catch (x: Exception) {
            ""
        }
    }

}