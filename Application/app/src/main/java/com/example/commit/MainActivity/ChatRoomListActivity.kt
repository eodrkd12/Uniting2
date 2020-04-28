package com.example.commit.MainActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.CategoryAdapter
import com.example.commit.Adapter.ChatRoomListAdapter
import com.example.commit.ListItem.ChatRoomListItem
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_open_chat_list.*
import kotlinx.android.synthetic.main.fragment_chat.*
import org.json.JSONArray
import org.json.JSONObject

/*class ChatRoomListActivity : AppCompatActivity() {

    lateinit var rvCategory: RecyclerView

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_chat_list)

        //val listChatRoom=findViewById<ListView>(R.id.list_chat_room)

        var chatRoomAdapter = ChatRoomListAdapter()

     //   listChatRoom.adapter=chatRoomAdapter

        rv_category.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        rv_category.adapter = CategoryAdapter(this)
        rv_category.setHasFixedSize(true)

        /*VolleyService.chatRoomListReq("uniting", this, { success ->
            chatRoomAdapter.clear()

            var chatRoomArray = success
            if (chatRoomArray!!.length() == 0) {

            } else {
                for (i in 0..chatRoomArray.length() - 1) {
                    var json = chatRoomArray[i] as JSONObject
                    var roomId = json.getString("room_id")
                    var cateName = json.getString("cate_name")
                    var maker = json.getString("maker")
                    var roomTitle = json.getString("room_title")
                    var limitNum = json.getInt("limit_num")
                    var universityName = json.getString("univ_name")
                    var curNum = json.getInt("cur_num")

                    chatRoomAdapter.addItem(roomId, cateName, maker, roomTitle, limitNum, universityName, curNum)
                }
            }
            chatRoomAdapter.notifyDataSetChanged()
        })*/


       /* listChatRoom.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var roomId=chatRoomAdapter.getRoomId(position)
            var intent= Intent(this,ChatActivity::class.java)
            intent.putExtra("room_id",roomId)
            startActivity(intent)
        }*/
    }
}*/