/*package com.example.commit.IntroActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.Adapter.UniversityAdapter
import com.example.commit.ListItem.UniversityItem
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_join1.*
import org.json.JSONArray
import org.json.JSONObject

class Join1Activity : AppCompatActivity() {

    var universityAdapter=UniversityAdapter()
    var universityArray:JSONArray?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join1)

        btn_university.setOnClickListener {
            var name = edit_university.text.toString()
            VolleyService.search_university(name, this, { success ->
                list_university.adapter=universityAdapter
                universityAdapter.clear()

                universityArray=success

                if(universityArray!!.length()==0) {
                    universityAdapter.addItem("검색 결과가 없습니다.",false)
                }
                else {
                    for (i in 0..universityArray!!.length() - 1) {
                        var json = JSONObject()
                        json = universityArray!![i] as JSONObject
                        universityAdapter.addItem(json.getString("univ_name"),true)
                    }
                }

                universityAdapter.notifyDataSetChanged()
            })
        }

        list_university.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                    var item=UniversityItem()
                    item=universityAdapter.getItem(position) as UniversityItem
                    if(item.enable) {
                        var university = universityArray!![position] as JSONObject
                        var webMail=university.getString("univ_mail")
                        var universityName=university.getString("univ_name")
                        var intent= Intent(this,Join2Activity::class.java)
                        intent.putExtra("univ_mail",webMail)
                        intent.putExtra("univ_name",universityName)
                        startActivity(intent)
                    }
        })
    }
}*/
