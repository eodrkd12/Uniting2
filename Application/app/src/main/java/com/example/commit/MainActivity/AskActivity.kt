package com.example.commit.MainActivity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.Volley
import com.example.commit.Adapter.ChatAdapter
import com.example.commit.Class.GMailSender
import com.example.commit.Class.UserInfo
import com.example.commit.Popup.ReportPopup
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_ask.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.HashMap

class AskActivity : AppCompatActivity() {

    var title=""
    var ask=""
    var nickname=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask)

        nickname=intent.getStringExtra("nickname")
        btn_ask.setOnClickListener {

            ask=text_ask.text.toString()
            title=text_askTitle.text.toString()

            var mailSender: GMailSender = GMailSender("eodrkd12@gmail.com", "ioioko123!","")
            mailSender.sendMail(
                "[문의]"+title
                , "문의 아이디:" + nickname+
                        "\n문의내용:"+ask

                , "eodrkd12@gmail.com"
            )
        }

    }
}