package com.example.commit.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.commit.Class.UserInfo
import com.example.commit.IntroActivity.LoginActivity
import com.example.commit.MainActivity.SettingActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService

class mypage_list (var memu :String)
var mypage_item = arrayListOf<mypage_list>()

class OptionFragment():Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view=inflater.inflate(R.layout.fragment_mypage,container,false)

        var textOption1=view.findViewById<TextView>(R.id.text_option1)
        var textOption3=view.findViewById<TextView>(R.id.text_option3)
        var textOption4=view.findViewById<TextView>(R.id.text_option4)
        var textOption5=view.findViewById<TextView>(R.id.text_option5)
        var textOption6=view.findViewById<TextView>(R.id.text_option6)
        var textOption7=view.findViewById<TextView>(R.id.text_option7)
        var textOption8=view.findViewById<TextView>(R.id.text_option8)
        var textOption9=view.findViewById<TextView>(R.id.text_option9)
        var textOption10=view.findViewById<TextView>(R.id.text_option10)
        var textOption11=view.findViewById<TextView>(R.id.text_option11)
        var switch=view.findViewById<Switch>(R.id.switch_onoff)
        var click=""

        VolleyService.getJoinDating(UserInfo.NICKNAME,activity!!.applicationContext,{success ->
            if(success==null) {
                switch.isChecked = false
            }
            else {
                switch.isChecked = true
            }

            switch.setOnCheckedChangeListener { buttonView, isChecked ->

                    VolleyService.datingOnOff(UserInfo.ID,UserInfo.NICKNAME,UserInfo.UNIV,UserInfo.DEPT,UserInfo.BIRTH,UserInfo.GENDER,UserInfo.HOBBY,UserInfo.PERSONALITY, switch.isChecked, activity!!.applicationContext,
                        { success ->
                            if(success=="On"){
                                Toast.makeText(activity!!.applicationContext,"만남 기능이 활성화 되었습니다.",Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(activity!!.applicationContext,"만남 기능이 비활성화 되었습니다.",Toast.LENGTH_SHORT).show()
                            }
                        })

            }
        })

        textOption1.setOnClickListener{
            click  = textOption1.text.toString()
            var intent = Intent(activity,SettingActivity::class.java)
            intent.putExtra("tag",click)
            startActivity(intent)

        }

        textOption3.setOnClickListener{
            click  = textOption3.text.toString()
            var intent = Intent(activity,SettingActivity::class.java)
            intent.putExtra("tag",click)
            startActivity(intent)

        }
        textOption4.setOnClickListener{
            click  = textOption4.text.toString()
            var intent = Intent(activity,SettingActivity::class.java)
            intent.putExtra("tag",click)
            startActivity(intent)

        }
        textOption5.setOnClickListener{
            click  = textOption5.text.toString()
            var intent = Intent(activity,SettingActivity::class.java)
            intent.putExtra("tag",click)
            startActivity(intent)

        }
        textOption6.setOnClickListener{
            click  = textOption6.text.toString()
            var intent = Intent(activity,SettingActivity::class.java)
            intent.putExtra("tag",click)
            startActivity(intent)

        }
        textOption7.setOnClickListener{
            click  = textOption7.text.toString()
            var intent = Intent(activity,SettingActivity::class.java)
            intent.putExtra("tag",click)
            startActivity(intent)

        }
        textOption8.setOnClickListener{
            click  = textOption8.text.toString()
            var intent = Intent(activity,SettingActivity::class.java)
            intent.putExtra("tag",click)
            startActivity(intent)

        }
        textOption9.setOnClickListener{
            click  = textOption9.text.toString()
            var intent = Intent(activity,SettingActivity::class.java)
            intent.putExtra("tag",click)
            startActivity(intent)

        }
        textOption10.setOnClickListener{
            var pref=activity!!.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            var editor=pref.edit()

            editor.clear()
            editor.commit()

            var intent= Intent(activity, LoginActivity::class.java)
            startActivity(intent)

        }

        textOption11.setOnClickListener{
            click  = textOption11.text.toString()
            var intent = Intent(activity,SettingActivity::class.java)
            intent.putExtra("tag",click)
            startActivity(intent)

        }

        /*var listView: ListView =view.findViewById(R.id.list_mypage)
        var mypageAdapter=MypageAdapter()
        listView.adapter=mypageAdapter

        mypageAdapter.addItem("프로필 보기")
        mypageAdapter.addItem("데이팅 on/off")
        mypageAdapter.addItem("알림 설정")
        mypageAdapter.addItem("문의하기")
        mypageAdapter.addItem("앱 버전")
        mypageAdapter.addItem("공지사항")
        mypageAdapter.addItem("커뮤니티 이용규칙")
        mypageAdapter.addItem("개인정보 처리방침")
        mypageAdapter.addItem("정보 수신 동의")
        mypageAdapter.addItem("로그아웃")
        mypageAdapter.addItem("회원탈퇴")


        listView.setOnItemClickListener { parent, view, position, id ->
            var tag=mypageAdapter.getName(position)



            var intent=Intent(activity!!,SettingActivity::class.java)
            intent.putExtra("tag",tag)
            startActivity(intent)
        }


        //mypageAdapter.getItemId()
        var click=View.OnClickListener{
            var intent= Intent(activity,  DatingActivity::class.java)
        }*/

        // Inflate the layout for this fragment
        return view
    }


}
