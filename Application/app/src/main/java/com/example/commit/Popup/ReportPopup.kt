package com.example.commit.Popup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.MotionEvent
import androidx.appcompat.app.AlertDialog
import com.example.commit.MainActivity.ChatActivity
import com.example.commit.MainActivity.ReportActivity
import com.example.commit.R
import kotlinx.android.synthetic.main.popup_report.*

class ReportPopup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popup_report)

        var nickname=intent.getStringExtra("nickname")

        text_report.setOnClickListener {
            val builder =
                AlertDialog.Builder(ContextThemeWrapper(this, R.style.Theme_AppCompat_Light_Dialog))
            builder.setTitle("${nickname}님을 신고하시겠습니까?")

            builder.setPositiveButton("신고하기") { _, _ ->
                var intent = Intent(this,ReportActivity::class.java)
                intent.putExtra("nickname",nickname)
                startActivity(intent)
            }
            builder.setNegativeButton("취소") { _, _ ->

            }
            builder.show()
        }
        text_cancel.setOnClickListener {
            finish()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event!!.action==MotionEvent.ACTION_OUTSIDE) return false
        else return true
    }

    override fun onBackPressed() {
        return;
    }
}
