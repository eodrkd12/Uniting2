package com.example.commit.IntroActivity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.parseColor
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.MajorSearchAdapter
import com.example.commit.Adapter.PersonalityAdapter
import com.example.commit.Adapter.UniversitySearchAdapter
import com.example.commit.Class.GMailSender
import com.example.commit.ListItem.Personality
import com.example.commit.ListItem.University
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_signup1.*
import kotlinx.android.synthetic.main.rvitem_chat_category.*
import org.json.JSONArray
import org.json.JSONObject

class Signup1Activity : AppCompatActivity() {
    companion object {
        var btnSignup1next: Button? = null
        var editUnivname:EditText? = null
        var editMajorname:EditText? = null
        var universityRV: RecyclerView? = null
        var majorRV: RecyclerView? = null
        var majorArray: JSONArray? = null
        var majorList: ArrayList<String> = arrayListOf()
        var univMail: String? = null
        var imm:InputMethodManager? = null

        var HANDLER: Handler? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup1)

        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        editMajorname = findViewById(R.id.edit_majorname)
        editUnivname = findViewById(R.id.edit_universityname)
        universityRV = findViewById(R.id.rv_university)
        majorRV = findViewById(R.id.rv_major)
        btnSignup1next = findViewById(R.id.btn_signup1next)

        var univArray: JSONArray? = null
        var univList: ArrayList<University> = arrayListOf()
        var univFilter: ArrayList<University> = arrayListOf()

        var majorFilter: ArrayList<String> = arrayListOf()

        VolleyService.search_university(this, {success ->
            univArray = success

            for(i in 0..univArray!!.length()-1) {
                var json = JSONObject()
                json = univArray!![i] as JSONObject

                var univName = json.getString("univ_name")
                var univMail = json.getString("univ_mail")

                univList.add(University(univName, univMail))
            }
        })

        /*edit_universityname.setOnFocusChangeListener {
            view, hasFocus-> if(hasFocus) {
            edit_universityname.setText(null)
            edit_universityname.setCursorVisible(true)
            edit_majorname.setText(null)
            btn_signup1next.setEnabled(false)
        }
        }*/

        edit_universityname.setOnTouchListener{ view, event->
            when(event.action) {
                MotionEvent.ACTION_DOWN-> {
                    edit_universityname.setFocusable(true)
                    edit_universityname.setText(null)
                    edit_universityname.setCursorVisible(true)
                    edit_majorname.setText(null)
                    btn_signup1next.setEnabled(false)
                    majorList.clear()
                    majorFilter.clear()
                }
            }
            false
        }

        edit_universityname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(univList.size == 0)
                {
                    //Toast.makeText(this@Signup1Activity, "없음", Toast.LENGTH_SHORT).show()
                }
                else{
                    univFilter.clear()
                    for(i in 0..univList.size-1) {
                        if((univList.get(i).univName!!.contains(edit_universityname.text) == true) && edit_universityname.text.toString() != "") {
                            univFilter.add(University(univList.get(i).univName, univList.get(i).univMail))
                        }
                    }
                    universityRV!!.setHasFixedSize(true)
                    universityRV!!.layoutManager = LinearLayoutManager(this@Signup1Activity, RecyclerView.VERTICAL, false)
                    universityRV!!.adapter = UniversitySearchAdapter(this@Signup1Activity, univFilter)
                }
            }
        })

        /*edit_majorname.setOnFocusChangeListener{ view, hasFocusd->
            if(hasFocusd) {
                edit_majorname.setCursorVisible(true)
                edit_majorname.setText(null)
                btn_signup1next.setEnabled(false)
            }
        }*/

        edit_majorname.setOnTouchListener{view, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN->{
                    edit_majorname.setCursorVisible(true)
                    edit_majorname.setText(null)
                    btn_signup1next.setEnabled(false)
                }
            }
            false
        }

        edit_majorname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(majorList.size == 0)
                {
                    //Toast.makeText(this@Signup1Activity, "없음", Toast.LENGTH_SHORT).show()
                }
                else{
                    majorFilter.clear()
                    for(i in 0..majorList.size-1) {
                        if((majorList.get(i)!!.contains(edit_majorname.text) == true) && edit_majorname.text.toString() != "") {
                            majorFilter.add(majorList.get(i))
                        }
                    }
                    majorRV!!.setHasFixedSize(true)
                    majorRV!!.layoutManager = LinearLayoutManager(this@Signup1Activity, RecyclerView.VERTICAL, false)
                    majorRV!!.adapter = MajorSearchAdapter(this@Signup1Activity, majorFilter)
                }
            }
        })

        btn_signup1next.setOnClickListener{
            var intent = Intent(this, Signup2Activity::class.java)
            intent.putExtra("univMail", univMail)
            intent.putExtra("univName", editUnivname!!.text.toString())
            intent.putExtra("univMajor", editMajorname!!.text.toString())
            startActivity(intent)
        }

        /*btn_sendmail.setOnClickListener{
            var webMail = edit_webmail.text.toString() + univAddr

            if(edit_webmail.text.toString() != "") {
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
                        , webMail
                    )

                    Toast.makeText(this,"인증번호를 전송하였습니다.",Toast.LENGTH_SHORT).show()
                })
            }
            else
            {
                Toast.makeText(this, "올바른 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        btn_certify.setOnClickListener{
            if (code==edit_certifynumber.text.toString()){
                //var intent= Intent(this,Join3Activity::class.java)
                code="만료"
                //Log.d("test","${universityName} / ${webMail}")
                //intent.putExtra("univ_name",universityName)
                //intent.putExtra("univ_mail",webMail)
                startActivity(intent)
            }
            else if(code=="만료"){
                Toast.makeText(this,"인증번호를 다시 전송해주세요.",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"인증번호를 확인해주세요",Toast.LENGTH_SHORT).show()
            }
        }*/

        HANDLER = object:Handler() {
            override fun handleMessage(msg: Message?) {
                when(msg!!.what) {
                    0-> {
                        majorList.clear()
                        VolleyService.search_department(editUnivname!!.text.toString(), this@Signup1Activity, { success ->
                            majorArray = success

                            for(i in 0..majorArray!!.length()-1) {
                                var json = JSONObject()
                                json = majorArray!![i] as JSONObject

                                var majorName = json.getString("dept_name")

                                majorList.add(majorName)
                            }
                        })
                    }
                }
            }
        }
    }
}
