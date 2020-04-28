package com.example.commit.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.commit.Class.GMailSender
import com.example.commit.Popup.ReportPopup
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_report.*

class ReportActivity : AppCompatActivity() {

    //var webMail:String?=null;
    var nickname= ""
    var reason=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        nickname=intent.getStringExtra("nickname")

        text_nickname.text="${nickname}님의 신고사유를 입력해주세요."
        btn_report.setOnClickListener {
          // webMail = edit_webmail.text.toString() + text_address.text.toString()

            reason=text_reason.text.toString()
                var mailSender: GMailSender = GMailSender("eodrkd12@gmail.com", "ioioko123!","")
                mailSender.sendMail(
                    "채팅방신고"
                    , "신고한 아이디:" + nickname+
                            "\n신고사유:"+reason

                   , "eodrkd12@gmail.com"
                )



            //Log.d("test", "메일전송 : ${webMail}")
        }
    }
    }