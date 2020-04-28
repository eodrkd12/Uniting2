/*
ListView
1 프로필 사진, 닉네임
2 내 정보
    (1) 이름
    (2) 나이(생년월일)
    (3) 학교
    (4) 학과
    (5) 이메일
3. 재인증
 */
package com.example.commit.MainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.Class.UserInfo
import com.example.commit.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

    }
}