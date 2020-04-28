package com.example.commit.MainActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.Class.UserInfo
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_join4.*
import kotlinx.android.synthetic.main.activity_makeroom.*
import kotlinx.android.synthetic.main.activity_signup2.view.*
import kotlinx.android.synthetic.main.dialog_personality.*
import org.jetbrains.anko.spinner

class MakeRoomActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makeroom)


        btn_room.setOnClickListener {

            var roomTitle = text_room_name.text.toString()
            var category = spinner_category.selectedItem.toString()
            var introduce = text_introduce.text.toString()
            var maxNum = Integer.parseInt(text_max.selectedItem.toString())

            if(roomTitle==""){
                text_warn.text="제목을 입력하세요"
                text_warn.visibility=View.VISIBLE

                return@setOnClickListener
            }


            VolleyService.createOpenChatReq(
                UserInfo.NICKNAME,
                roomTitle,
                category,
                UserInfo.UNIV,
                introduce,
                maxNum,
                this,
                { success ->
                    var intent = Intent(this, OpenChatListActivity::class.java)
                    intent.putExtra("room_id", success!!.get("room_id").toString())
                    startActivity(intent)
                })
        }
    }
}
