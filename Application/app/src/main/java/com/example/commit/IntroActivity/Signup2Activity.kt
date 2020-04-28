package com.example.commit.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.commit.Class.GMailSender
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_signup2.*

class Signup2Activity : AppCompatActivity() {

    var code:String = ""
    var webMail:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2)

        var intent = intent
        var univMail:String = intent.getStringExtra("univMail")
        var univName:String = intent.getStringExtra("univName")
        var univMajor:String = intent.getStringExtra("univMajor")

        text_univaddr.setText(univMail)


        text_univaddr.text = univMail
        text_univaddr.bringToFront()

        btn_sendmail.setOnClickListener{
            webMail = edit_univid.text.toString() + univMail

            VolleyService.codeReq(this, { success ->
                code=success

                //이메일로 인증번호 보내기
                var mailSender: GMailSender = GMailSender("ljs950113@gmail.com", "limjs165879!", code)
                mailSender.sendMail(
                    "Uniting 이메일 인증"
                    , "안녕하세요.\n" +
                            "아래 인증 코드를 애플리케이션에서 입력하여 회원가입을 진행해주십시오.\n" +
                            "인증코드 : [${code}]\n" +
                            "감사합니다."
                    , webMail
                )

                Toast.makeText(this,"인증번호를 전송하였습니다.",Toast.LENGTH_SHORT).show()
            })
        }

        btn_certify.setOnClickListener{
            if (code==edit_certifynumber.text.toString()){
                var intent= Intent(this,Signup3Activity::class.java)
                code="만료"
                //Log.d("test","${universityName} / ${webMail}")
                intent.putExtra("univName", univName)
                intent.putExtra("univMajor", univMajor)
                intent.putExtra("univMail", webMail)
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
}
