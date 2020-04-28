/*
Intro Activity
1. 로고
2. 회원가입 버튼 회원가입 화면으로 전환(JoinActivity)
3. 로그인 버튼 -> 로그인 화면으로 전환(LoginActivity)
 */
/*package com.example.commit.IntroActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.R

import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    //onCreate : 액티비티가 생성될 때 실행되는 메소드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView : 액티비티와 연결되는 레이아웃파일을 설정
        setContentView(R.layout.activity_intro)

        text_logo.setOnClickListener {
            var intent=Intent(this,Join5Activity::class.java)
            startActivity(intent)
        }

        //버튼에 클릭리스너를 연결해주는 코드
        btn_join.setOnClickListener{
            //Intent : 다른 액티비티로 전환할 때 사용하는 클래스로 startActivity 메소드를 사용하여 액티비티 전환
            //Join1Activity로 전환하는 코드
            var intent:Intent=Intent(this,Join1Activity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener{
            var intent:Intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}*/
