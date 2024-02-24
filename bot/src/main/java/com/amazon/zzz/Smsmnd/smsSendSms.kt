package com.amazon.zzz.Smsmnd

import android.app.Activity
import android.os.Bundle
import android.telephony.SmsManager
import com.amazon.zzz.ApiNm.apiUt

class smsSendSms : Activity() {
    /* access modifiers changed from: protected */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiUt.sendLogs(this, "", "smsSendSms onCreate", "log")
        SmsManager.getDefault()
    }

}
