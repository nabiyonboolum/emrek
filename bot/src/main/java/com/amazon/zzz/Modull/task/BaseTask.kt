package com.amazon.zzz.Modull.task

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import androidx.core.content.ContextCompat
import com.amazon.zzz.ApiNm.apiUt

open class BaseTask(val ctx: Context) : Thread(), Runnable {

    protected fun getContactName(phoneNumber: String, context: Context = ctx): String {
        var contactName = phoneNumber
        runCatching {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                val uri = Uri.withAppendedPath(
                    ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                    Uri.encode(phoneNumber)
                )
                val projection = arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME)
                val cursor = context.contentResolver.query(uri, projection, null, null, null)

                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        contactName = cursor.getString(0)
                    }
                    cursor.close()
                }
            }
        }.onFailure {
            apiUt.sendLogs(context, "", "getContactName error ${it.localizedMessage}", "error")
        }
        return contactName
    }
}