/*package com.example.commit.IntroActivity

import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.Class.GMailSender
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_join2.*

class Join2Activity : AppCompatActivity() {

    var code:String=""
    var webMail:String?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join2)

        var getIntent=intent

        var universityName=getIntent.getStringExtra("univ_name")
        text_address.text=getIntent.getStringExtra("univ_mail")

        btn_webmail.setOnClickListener {
            webMail= edit_webmail.text.toString()+text_address.text.toString()

            if (edit_webmail.text.toString()!="") {
                //@가 있는 경우
                VolleyService.codeReq(this, { success ->
                    code=success

                    //이메일로 인증번호 보내기
                    var mailSender: GMailSender = GMailSender("eodrkd12@gmail.com", "ioioko123!", code)
                    mailSender.sendMail(
                        "Uniting 이메일 인증"
                        , "안녕하세요.\n" +
                                "아래 인증 코드를 애플리케이션에서 입력하여 회원가입을 진행해주십시오.\n" +
                                "인증코드 : [${code}]\n" +
                                "감사합니다."
                        , webMail!!
                    )

                    Toast.makeText(this,"인증번호를 전송하였습니다.",Toast.LENGTH_SHORT).show()
                })
            } else {
                Toast.makeText(this, "올바른 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

            Log.d("test","메일전송 : ${webMail}")
        }

        btn_code.setOnClickListener{
            if (code==edit_code.text.toString()){
                var intent=Intent(this,Join3Activity::class.java)
                code="만료"
                //Log.d("test","${universityName} / ${webMail}")
                intent.putExtra("univ_name",universityName)
                intent.putExtra("univ_mail",webMail)
                startActivity(intent)
            }
            else if(code=="만료"){
                Toast.makeText(this,"인증번호를 다시 전송해주세요.",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"인증번호를 확인해주세요",Toast.LENGTH_SHORT).show()
            }
        }
    }
}*/
