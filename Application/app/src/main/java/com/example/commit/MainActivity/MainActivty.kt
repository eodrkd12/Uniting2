/*
1. 최상단 : 학교로고(좌측) 앱로고(중앙)

2. 최상단 바로 밑(*ContentsActivity만) : 돋보기 버튼(우측) 누를 시, 좌측에 검색어 입력칸

3. 가운데 : 탭 내용
    3-1. 홈(ContentsActivity로 전환)
        (1) 데이팅 버튼 : DatingActivity로 전환
        (2) 스터디 버튼 : StudyActivity로 전환
        (3) 장터 버튼 : MarketActivity로 전환
        (4) 아무거나 버튼 : AnyActivity로 전환
        (5) 광고 배너(처음엔 없음)
    3-2. 채팅
        (1) 채팅 목록 : 데이팅, 스터디, 장터, 아무거나 별로 채팅목록을 보여줌
                      목록 항목 클릭 시, ChattingActivity로 전환
            * 데이팅은 최대 한명만 채팅을 유지할 수 있다.
    3-3, 알림
        (1) 댓글, 답글, 채팅신청 등 알림을 리스트뷰에 보여줌
    3-4. 설정(리스트뷰)
        (1) 프로필 관리 -> ProfileActivity로 전환 -> TextView
        (2) 게시물 관리 -> MyPostActivity로 전환 -> TextView
        (3) 데이팅 매칭 on/off -> TextView + switch
        (4) 알림설정 -> AlarmActivity로 전환
        (5) 문의하기 -> QuestionActivity로 전환
        (6) 공지사항 -> NoticeActivity로 전환
        (7) 이용규칙 -> RuleActivity로 전환
        (8) 개인정보처리방침 -> PolicyActivity로 전환
        (9) 앱버전 -> TextView

4. 최하단 : 탭(홈,채팅,설정)
*/
package com.example.commit.MainActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.commit.R
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.Adapter.MyPagerAdapter
import com.example.commit.Fragment.*
import com.example.commit.IntroActivity.LoginActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bnv_main)
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener)

        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
        }

    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.home -> {
                val fragment = HomeFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.chat -> {
                val fragment = ChatFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.option -> {
                val fragment = OptionFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.cafeteria -> {
                val fragment = CafeteriaFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        var inflater = getMenuInflater()
        inflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        var title = item!!.title.toString()
        when (title) {
            "로그아웃" -> {
                var pref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                var editor = pref.edit()

                editor.clear()
                editor.commit()

                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}