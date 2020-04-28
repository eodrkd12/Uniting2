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
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.item_university.view.*
import kotlinx.android.synthetic.main.rvitem_chat_category.view.*
import org.json.JSONArray
import java.util.logging.Handler

class MajorSearchAdapter(val context: Context, val majorFilter:ArrayList<String>) : RecyclerView.Adapter<MajorSearchAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return majorFilter.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MajorSearchAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_university, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MajorSearchAdapter.ViewHolder, position: Int) {
        holder.itemView.text_university.text = majorFilter.get(position)
        holder.itemView.setOnClickListener {
            val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_AppCompat_Light_Dialog))
            builder.setTitle("선택한 학과가 맞습니까?")
            builder.setMessage(majorFilter.get(position))

            builder.setNegativeButton("확인") { dialog, id->
                Signup1Activity.editMajorname!!.setText(majorFilter.get(position))
                Signup1Activity.editMajorname!!.setCursorVisible(false)
                Signup1Activity.btnSignup1next!!.setEnabled(true)
                Signup1Activity.majorRV!!.adapter=null
                Signup1Activity.imm!!.hideSoftInputFromWindow(Signup1Activity.editMajorname!!.windowToken, 0)
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