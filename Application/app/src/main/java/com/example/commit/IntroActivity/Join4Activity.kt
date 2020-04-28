/*
package com.example.commit.IntroActivity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_join4.*
import java.util.*
import kotlin.collections.ArrayList

class Join4Activity : AppCompatActivity() {

    var idCheck:Int = 0
    var nicknameCheck:Int =0

    var yearList=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join4)

        var getIntent=intent

        var universityName=getIntent.getStringExtra("univ_name")
        var departmentName=getIntent.getStringExtra("dept_name")
        var webMail=getIntent.getStringExtra("univ_mail")
        var birthday:String?=null

        text_webmail_edited.text=webMail

        btn_id.setOnClickListener{
            VolleyService.idCheckReq(edit_id.text.toString(),this,{success ->
                idCheck=success
            })
        }

        btn_nickname.setOnClickListener{
            VolleyService.nicknameCheckReq(edit_nickname.text.toString(),this,{success ->
                nicknameCheck=success
            })
        }

        //날짜 입력
        edit_birthday.setOnClickListener {
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            var date_listener  = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    edit_birthday.setText("${year}-${month + 1}-${dayOfMonth}")
                    birthday="${year}"
                    if((month+1)<10) birthday+="0${month+1}"
                    else birthday+=month+1

                    if(dayOfMonth<10) birthday+="0${dayOfMonth}"
                    else birthday+=dayOfMonth
                }
            }

            var builder = DatePickerDialog(this, date_listener, year, month, day)
            builder.show()
        }

        //스피너에 연도 입력
        for(i in 2010..2025){
            yearList.add(i.toString())
        }
        var adapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,yearList)
        spinner_enter_year.adapter=adapter

        btn_confirm.setOnClickListener{
            var id=edit_id.text.toString()
            var pw=edit_pw.text.toString()
            var pwCheck=edit_pw_check.text.toString()
            var name=edit_name.text.toString()
            var nickname=edit_nickname.text.toString()
            var gender=radio_man.isChecked
            var enterYear=spinner_enter_year.selectedItem.toString()

            if(idCheck==0)
                Toast.makeText(this,"ID를 확인해주세요.",Toast.LENGTH_SHORT).show()
            else if(pw!=pwCheck)
                Toast.makeText(this,"비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show()
            else if(name=="")
                Toast.makeText(this,"이름을 입력해주세요.",Toast.LENGTH_SHORT).show()
            else if(birthday=="")
                Toast.makeText(this,"생일을 입력해주세요",Toast.LENGTH_SHORT).show()
            else if(nicknameCheck==0)
                Toast.makeText(this,"닉네임을 확인해주세요.",Toast.LENGTH_SHORT).show()
            else{
                var intent= Intent(this,Join5Activity::class.java)
                intent.putExtra("id",id)
                intent.putExtra("pw",pw)
                intent.putExtra("name",name)
                intent.putExtra("birthday",birthday)
                intent.putExtra("nickname",nickname)
                if(gender) {
                    intent.putExtra("gender", "M")
                    Log.d("test","남자")
                }
                else {
                    intent.putExtra("gender", "W")
                    Log.d("test","여자")
                }
                intent.putExtra("univ_name",universityName)
                intent.putExtra("dept_name",departmentName)
                intent.putExtra("univ_mail",webMail)
                intent.putExtra("enter_year",enterYear)
                startActivity(intent)
            }
        }
    }
}
*/
