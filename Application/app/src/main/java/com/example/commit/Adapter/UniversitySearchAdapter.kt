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
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Class.UserInfo
import com.example.commit.IntroActivity.Signup1Activity
import com.example.commit.ListItem.Categoryitem
import com.example.commit.ListItem.University
import com.example.commit.MainActivity.OpenChatListActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_signup1.view.*
import kotlinx.android.synthetic.main.item_university.view.*
import kotlinx.android.synthetic.main.rvitem_chat_category.view.*
import org.json.JSONArray
import java.util.logging.Handler

class UniversitySearchAdapter(val context: Context, val univFilter:ArrayList<University>) : RecyclerView.Adapter<UniversitySearchAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return univFilter.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversitySearchAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_university, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UniversitySearchAdapter.ViewHolder, position: Int) {
        holder.itemView.text_university.text = univFilter.get(position).univName
        holder.view.setOnClickListener{
            val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_AppCompat_Light_Dialog))
            builder.setTitle("선택한 학교가 맞습니까?")
            builder.setMessage(univFilter.get(position).univName)

            builder.setNegativeButton("확인") { dialog, id->
                Signup1Activity.univMail = univFilter.get(position).univMail
                Signup1Activity.editUnivname!!.setText(univFilter.get(position).univName)
                Signup1Activity.editUnivname!!.setCursorVisible(false)
                Signup1Activity.universityRV!!.adapter=null
                Signup1Activity.imm!!.hideSoftInputFromWindow(Signup1Activity.editUnivname!!.windowToken, 0)
                //Signup1Activity.editUnivname!!.setEnabled(false)
                //Toast.makeText(context, univFilter.size.toString(), Toast.LENGTH_SHORT).show()
                var handler=Signup1Activity.HANDLER
                var msg:Message=handler!!.obtainMessage()
                msg.what=0
                handler.sendMessage(msg)
            }
            builder.setPositiveButton("취소") { dialog, id ->

            }
            builder.show()
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    }

}