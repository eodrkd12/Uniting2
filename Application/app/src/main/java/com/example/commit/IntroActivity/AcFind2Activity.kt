package com.example.commit.IntroActivity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_acfind2.*

class AcFind2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acfind2)

        button_num.setOnClickListener {
            var s:String = editText4.text.toString()
            var number:Int = Integer.parseInt(s)

            VolleyService.check_num(number,this,{success ->
                when(success){
                    0 ->{
                        //통신 실패
                        Toast.makeText(this,"서버와의 통신에 실패했습니다.",Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                        //인증번호 오류
                        Toast.makeText(this, "인증번호를 다시 확인해주세요",Toast.LENGTH_SHORT).show()
                    }
                    2 -> {
                        //인정성공
                        //미구현
                    }
                }
            })
        } //인증번호

        button_check.setOnClickListener {
            var pw:String = editText5.text.toString()
            var pw2:String = editText6.text.toString()

            VolleyService.change_pw(pw,pw2,this,{success ->
                when(success){
                    0 ->{
                        //통신 실패
                        Toast.makeText(this,"서버와의 통신에 실패했습니다.",Toast.LENGTH_SHORT).show()
                    }
                    1 ->{
                        //비밀번호 오류
                        Toast.makeText(this,"비밀번호가 잘못되었습니다.",Toast.LENGTH_SHORT).show()
                    }
                    2 ->{
                        //비밀번호 오류
                        Toast.makeText(this, "비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show()
                    }
                    3 ->{
                        //성공
                        // 비밀번호 변경 (미구현)
                    }
                }
            })
        } //비밀번호 변경
    }
}