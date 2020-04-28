package com.example.commit.MainActivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.CategoryAdapter
import com.example.commit.Adapter.ChatRoomListAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_open_chat_list.*
import org.json.JSONObject
import com.example.commit.ListItem.ChatRoomListItem


class OpenChatListActivity : AppCompatActivity() {

    init {
        INSTANCE = this
    }

    companion object {
        var CATEGORY: String = "전체"
        var INSTANCE: OpenChatListActivity? = null
        var HANDLER: Handler? = null
        var OPENCHATRV: RecyclerView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_chat_list)
        OPENCHATRV = findViewById(R.id.rv_open)


        btn_make.setOnClickListener(View.OnClickListener() {
            var intent = Intent(this, MakeRoomActivity::class.java)
            startActivity(intent)
        }
        )

        rv_category.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_category.adapter = CategoryAdapter(this)
        rv_category.setHasFixedSize(true)

        var chatRoomAdapter = ChatRoomListAdapter(this)
        rv_open.adapter=chatRoomAdapter

        var layoutManager=LinearLayoutManager(this)
        rv_open.layoutManager=layoutManager
        rv_open.setHasFixedSize(true)

        VolleyService.openChatRoomListReq(UserInfo.UNIV, CATEGORY, this, { success ->
            chatRoomAdapter.clear()

            var chatRoomArray = success
            if (chatRoomArray!!.length() == 0) {

            } else {
                for (i in 0..chatRoomArray.length() - 1) {
                    var json = chatRoomArray[i] as JSONObject
                    var roomId = json.getString("room_id")
                    var category = json.getString("cate_name")
                    var maker = json.getString("maker")
                    var roomTitle = json.getString("room_title")
                    var limitNum = json.getInt("limit_num")
                    var universityName = json.getString("univ_name")
                    var curNum = json.getInt("cur_num")
                    var introduce = json.getString("introduce")

                    chatRoomAdapter.addItem(
                        roomId,
                        category,
                        maker,
                        roomTitle,
                        limitNum,
                        universityName,
                        curNum,
                        introduce
                    )
                }
                chatRoomAdapter.notifyDataSetChanged()

            }


            HANDLER = object : Handler() {
                override fun handleMessage(msg: Message?) {
                    Log.d("test", "카테고리 메시지 도착 : ${msg!!.what}")
                    when (msg!!.what) {
                        0 -> {
                            //카테고리 방 갱신
                            VolleyService.openChatRoomListReq(
                                UserInfo.UNIV,
                                CATEGORY,
                                INSTANCE!!,
                                { success ->
                                    rv_open.adapter = chatRoomAdapter
                                    chatRoomAdapter.clear()

                                    var chatRoomArray = success
                                    if (chatRoomArray!!.length() == 0) {

                                    } else {
                                        for (i in 0..chatRoomArray.length() - 1) {
                                            var json = chatRoomArray[i] as JSONObject
                                            var roomId = json.getString("room_id")
                                            var category = json.getString("cate_name")
                                            var maker = json.getString("maker")
                                            var roomTitle = json.getString("room_title")
                                            var limitNum = json.getInt("limit_num")
                                            var universityName = json.getString("univ_name")
                                            var curNum = json.getInt("cur_num")
                                            var introduce = json.getString("introduce")
                                            chatRoomAdapter.addItem(
                                                roomId,
                                                category,
                                                maker,
                                                roomTitle,
                                                limitNum,
                                                universityName,
                                                curNum,
                                                introduce
                                            )
                                        }
                                    }
                                })

                            chatRoomAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })

        //var openchatArray: JSONArray? = null
        var openchatList: ArrayList<ChatRoomListItem> = arrayListOf()
        var openchatFilter: ArrayList<ChatRoomListItem> = arrayListOf()

        VolleyService.getSearchReq(UserInfo.UNIV,this, {success ->
          //  chatRoomAdapter.clear()
            var chatRoomArray = success

                for (i in 0..chatRoomArray!!.length() - 1) {
                    var json = chatRoomArray!![i] as JSONObject
                    var roomId = json.getString("room_id")
                    var category = json.getString("cate_name")
                    var maker = json.getString("maker")
                    var roomTitle = json.getString("room_title")
                    var limitNum = json.getInt("limit_num")
                    var universityName = json.getString("univ_name")
                    var curNum = json.getInt("cur_num")
                    var introduce = json.getString("introduce")

                   var item:ChatRoomListItem = ChatRoomListItem()
                    item.roomId=roomId
                    item.cateName=category
                    item.maker=maker
                    item.roomTitle=roomTitle
                    item.limitNum=limitNum
                    item.universityName=universityName
                    item.curNum=curNum
                    item.introduce=introduce

                   openchatList.add(item)

                }
                Toast.makeText(this@OpenChatListActivity, openchatList.size.toString(), Toast.LENGTH_SHORT).show()


        })

        text_search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (openchatList.size == 0)  {

                }else{
                    rv_open.adapter = chatRoomAdapter
                    chatRoomAdapter.clear()

                    openchatFilter.clear()

                    for (i in 0..openchatList.size - 1) {
                        if ((openchatList.get(i).roomTitle!!.contains(text_search.text) == true) && text_search.text.toString() != "") {


                            var item: ChatRoomListItem = ChatRoomListItem()
                            item.roomId = openchatList.get(i).roomId
                            item.cateName = openchatList.get(i).cateName
                            item.maker = openchatList.get(i).maker
                            item.roomTitle = openchatList.get(i).roomTitle
                            item.limitNum = openchatList.get(i).limitNum
                            item.universityName = openchatList.get(i).universityName
                            item.curNum = openchatList.get(i).curNum
                            item.introduce = openchatList.get(i).introduce

                            openchatFilter.add(item)


                            var roomId = item.roomId
                            var cateName = item.cateName
                            var maker = item.maker
                            var roomTitle = item.roomTitle
                            var limitNum = item.limitNum
                            var universityName = item.universityName
                            var curNum = item.curNum
                            var introduce = item.introduce


                            chatRoomAdapter.addItem(
                                roomId.toString(),
                                cateName.toString(),
                                maker.toString(),
                                roomTitle.toString(),
                                limitNum!!,
                                universityName.toString(),
                                curNum!!,
                                introduce.toString()
                            )

                            chatRoomAdapter.notifyDataSetChanged()
                        }
                        else if( text_search.text.toString() == "" ){
                            var item: ChatRoomListItem = ChatRoomListItem()
                            item.roomId = openchatList.get(i).roomId
                            item.cateName = openchatList.get(i).cateName
                            item.maker = openchatList.get(i).maker
                            item.roomTitle = openchatList.get(i).roomTitle
                            item.limitNum = openchatList.get(i).limitNum
                            item.universityName = openchatList.get(i).universityName
                            item.curNum = openchatList.get(i).curNum
                            item.introduce = openchatList.get(i).introduce

                            var roomId = openchatList.get(i).roomId
                            var cateName = openchatList.get(i).cateName
                            var maker = openchatList.get(i).maker
                            var roomTitle = openchatList.get(i).roomTitle
                            var limitNum = openchatList.get(i).limitNum
                            var universityName = openchatList.get(i).universityName
                            var curNum = openchatList.get(i).curNum
                            var introduce = openchatList.get(i).introduce


                            chatRoomAdapter.addItem(
                                roomId.toString(),
                                cateName.toString(),
                                maker.toString(),
                                roomTitle.toString(),
                                limitNum!!,
                                universityName.toString(),
                                curNum!!,
                                introduce.toString()
                            )

                            chatRoomAdapter.notifyDataSetChanged()


                        }

                    }
                    rv_open.setHasFixedSize(true)

                   Toast.makeText(this@OpenChatListActivity, openchatFilter.size.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })


    }
}
