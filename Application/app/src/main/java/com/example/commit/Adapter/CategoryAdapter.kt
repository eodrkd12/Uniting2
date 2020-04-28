package com.example.commit.Adapter

import android.content.Context
import android.os.Message
import android.util.Log
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.Categoryitem
import com.example.commit.MainActivity.OpenChatListActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.rvitem_chat_category.view.*
import org.json.JSONArray
import java.util.logging.Handler

class CategoryAdapter(val context: Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    val categoryList = arrayListOf<Categoryitem>(Categoryitem("전체"), Categoryitem("취미"), Categoryitem("스터디"), Categoryitem("취/창업"), Categoryitem("고민상담"), Categoryitem("아무말"))

    override fun getItemCount(): Int {
        return categoryList.count();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rvitem_chat_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bindItems(categoryList.get(position))
        /*holder.view.setOnClickListener{
            val intent = Intent(context, InformActivity::class.java)
            context.startActivity(intent)
        }*/
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: Categoryitem) {
            itemView.btn_category_lable.text = data.categoryname
            if(data.categoryname=="취미"){
                itemView.btn_category.setImageResource(R.drawable.play_icon)
            }
            else if(data.categoryname=="스터디"){
                itemView.btn_category.setImageResource(R.drawable.book_icon)
            }
            else if(data.categoryname=="취/창업"){
                itemView.btn_category.setImageResource(R.drawable.work_icon)
            }
            else if(data.categoryname=="고민상담"){
                itemView.btn_category.setImageResource(R.drawable.tea_icon)
            }
            else
                itemView.btn_category.setImageResource(R.drawable.add_icon)

            itemView.btn_category.setOnClickListener {
                OpenChatListActivity.CATEGORY=data.categoryname!!
                var handler=OpenChatListActivity.HANDLER
                var msg:Message=handler!!.obtainMessage()
                msg.what=0
                handler.sendMessage(msg)
            }
        }
    }

}
