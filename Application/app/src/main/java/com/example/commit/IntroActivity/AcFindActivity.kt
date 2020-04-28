/*
AcFindActivity
1. (ID 찾는 버튼)이메일 입력 및 확인 -> (팝업)ID 확인가능
2. (P/W 찾는 버튼) ID/이메일 입력 -> 이메일로 인증번호 보냄
                                -> (새로운 엑티비티 전환 후) -> 인증번호 입력 후 확인  -> 새로운 비밀번호 입력 및 확인 -> LoginActivity 로 이동

*/
package com.example.commit.IntroActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_acfind.*

class AcFindActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acfind) //엑티비티파인드

       btn_search.setOnClickListener {
           var email:String=editText.text.toString()

           VolleyService.findReq(email,this, {success ->
               when(success){
                   0 -> {
                       //통신 실패
                       Toast.makeText(this,"서버와의 통신에 실패했습니다.",Toast.LENGTH_SHORT).show()
                   }
                   1 ->{
                       // 이메일 오류
                       Toast.makeText(this,"email을 확인해주세요",Toast.LENGTH_SHORT).show()
                   }
                   2 ->{
                       //email 일치(성공)
                       //////////////////////이구간은 이메일로 내가 잊어버린 ID를 보내주는 것이다.
                   }
               }
           })
       } // 이 함수는 ID만 잊어버렸을때 email입력하여 email로 ID를 보내주는

        button_search_pw.setOnClickListener {
            var id:String=editText2.text.toString()
            var email:String=editText3.text.toString()

            VolleyService.findReq2(id, email,this, {success ->
                when(success){
                    0 ->{
                        //통신실패
                        Toast.makeText(this, "서버와의 통신에 실패했습니다.",Toast.LENGTH_SHORT).show()
                    }
                    1 ->{
                        //인증실패
                        Toast.makeText(this, "ID를 확인해 주세요",Toast.LENGTH_SHORT).show()
                    }
                    2 ->{
                        //인증실패
                        Toast.makeText(this, "email을 확인해 주세요",Toast.LENGTH_SHORT).show()
                    }
                    3->{
                        //인증성공
                        var intent:Intent = Intent(this,AcFind2Activity::class.java)
                        intent.putExtra("id",id)
                        intent.putExtra("email",email)
                        startActivity(intent)
                        // 인증번호가 이메일로 가게끔하는건 미구현
                    }
                }
            })
        } // 이 함수는 pw을 찾기위함 AcFindActivity2로 이동하며 인증번호가 email로 보내어진다.
    }
}