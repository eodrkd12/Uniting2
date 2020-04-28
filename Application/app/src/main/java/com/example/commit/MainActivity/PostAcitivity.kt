/*
뒤로가기
게시글 영역
1. 글쓴이(클릭 시 채팅걸기)
2. 제목
3. 내용
4. 댓글(답글보기/달기)
 */
package com.example.commit.MainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.R

class PostAcitivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_acitivity)
    }
}
