/*
리스트뷰
스터디, 장터, 아무거나 별로 영역구분
1. 게시글 리스트 -> PostActivity(관리자 모드)
*/
package com.example.commit.MainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.R

class MyPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_post)
    }
}
