package com.example.commit.MainActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commit.Adapter.ChatAdapter
import com.example.commit.Adapter.DatingAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_dating.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class DatingActivity : AppCompatActivity() {

    var datingAdapter = DatingAdapter(this)
    var datingArray: JSONArray? = null

    var layoutManager=LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dating)

        text_dating.text=UserInfo.UNIV

        VolleyService.datingUserReq(UserInfo.NICKNAME, UserInfo.GENDER, UserInfo.UNIV, this, { success ->
            rv_dating.adapter = datingAdapter
            rv_dating.layoutManager=layoutManager
            rv_dating.setHasFixedSize(true)
            datingAdapter.clear()

            datingArray = success

            if (datingArray!!.length() == 0) {
                text_partner_null.visibility= View.VISIBLE
            } else {
                text_partner_null.visibility= View.GONE
                for (i in 0..datingArray!!.length() - 1) {
                    var json = JSONObject()
                    json = datingArray!![i] as JSONObject
                    var nickname = json.getString("user_nickname")
                    var department = json.getString("dept_name")
                    //현재 연도 구하기
                    var calendar = GregorianCalendar(Locale.KOREA)
                    var year = calendar.get(Calendar.YEAR)
                    //이용자 생일 구하기
                    var birthday = json.getString("user_birthday")
                    birthday = birthday.substring(0, 4)

                    var hobby=json.getString("user_hobby")
                    var personality=json.getString("user_personality")
                    //이용자 나이 계산
                    var age = year - Integer.parseInt(birthday) + 1
                    datingAdapter.addItem(nickname, department, age, hobby, personality)
                }
            }

            datingAdapter.notifyDataSetChanged()
        })
    }
}