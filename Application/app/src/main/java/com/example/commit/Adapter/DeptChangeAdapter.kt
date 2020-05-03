package com.example.commit.Adapter

import android.content.Context
import android.os.Message
import android.util.Log
import android.view.ContextThemeWrapper
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Class.UserInfo
import com.example.commit.IntroActivity.Signup1Activity
import com.example.commit.ListItem.Categoryitem
import com.example.commit.ListItem.University
import com.example.commit.MainActivity.OpenChatListActivity
import com.example.commit.MainActivity.SettingActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.item_university.view.*
import kotlinx.android.synthetic.main.rvitem_chat_category.view.*
import org.json.JSONArray
import java.util.logging.Handler

class DeptChangeAdapter(val context: Context, val deptFilter:ArrayList<String>) : RecyclerView.Adapter<DeptChangeAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return deptFilter.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeptChangeAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_university, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DeptChangeAdapter.ViewHolder, position: Int) {
        holder.itemView.text_university.text = deptFilter.get(position)
        holder.itemView.setOnClickListener {
            val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_AppCompat_Light_Dialog))
            builder.setTitle("선택한 학과가 맞습니까?")
            builder.setMessage(deptFilter.get(position))

            builder.setNegativeButton("확인") { dialog, id->
                SettingActivity.dialogEditDept!!.setText(deptFilter.get(position))
                SettingActivity.dialogEditDept!!.setCursorVisible(false)
                SettingActivity.dialogRvDept!!.adapter = null
                SettingActivity.imm!!.hideSoftInputFromWindow(SettingActivity.dialogEditDept!!.windowToken, 0)
                SettingActivity.dialogBtnChangeDept!!.setEnabled(true)
                /*Signup1Activity.editDeptname!!.setText(deptFilter.get(position))
                Signup1Activity.editDeptname!!.setCursorVisible(false)
                Signup1Activity.btnSignup1next!!.setEnabled(true)
                Signup1Activity.rvDept!!.adapter=null
                Signup1Activity.imm!!.hideSoftInputFromWindow(Signup1Activity.editDeptname!!.windowToken, 0)*/
                //Signup1Activity.editMajorname!!.setEnabled(false)
            }
            builder.setPositiveButton("취소") { dialog, id ->

            }
            builder.show()
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    }

}