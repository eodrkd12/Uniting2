/*package com.example.commit.IntroActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.Adapter.DepartmentAdapter
import com.example.commit.ListItem.DepartmentItem
import com.example.commit.ListItem.UniversityItem
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_join1.*
import kotlinx.android.synthetic.main.activity_join3.*
import org.json.JSONArray
import org.json.JSONObject

class Join3Activity : AppCompatActivity() {

    var departmentAdapter = DepartmentAdapter()
    var departmentArray: JSONArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join3)

        var getIntent=intent
        var universityName=getIntent.getStringExtra("univ_name")
        var webMail=getIntent.getStringExtra("univ_mail")

        btn_department.setOnClickListener {
            var departmentName = edit_department.text.toString()
            VolleyService.search_department(universityName!!,departmentName, this, { success ->
                list_department.adapter = departmentAdapter
                departmentAdapter.clear()

                departmentArray = success

                if (departmentArray!!.length() == 0) {
                    departmentAdapter.addItem("검색 결과가 없습니다.", false)
                } else {
                    for (i in 0..departmentArray!!.length() - 1) {
                        var json = JSONObject()
                        json = departmentArray!![i] as JSONObject
                        departmentAdapter.addItem(json.getString("dept_name"), true)
                    }
                }

                departmentAdapter.notifyDataSetChanged()
            })
        }

        list_department.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            var item= DepartmentItem()
            item=departmentAdapter.getItem(position) as DepartmentItem
            if(item.enable) {
                var department = departmentArray!![position] as JSONObject
                var departmentName=department.getString("dept_name")

                var intent= Intent(this,Join4Activity::class.java)
                intent.putExtra("dept_name",departmentName)
                intent.putExtra("univ_name",universityName)
                intent.putExtra("univ_mail",webMail)
                startActivity(intent)
            }
        })
    }
}*/
