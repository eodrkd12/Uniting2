package com.example.commit.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.ChatRoomListAdapter
import com.example.commit.Adapter.MyChatListAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.MyChatRoomListItem
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONObject

class ChatFragment() : Fragment() {

    var myChatAdapter: MyChatListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_chat, container, false)

        var view = inflater.inflate(R.layout.fragment_chat, container, false)

        myChatAdapter = MyChatListAdapter(context!!)
        var rvRoom = view.findViewById(R.id.rv_room) as RecyclerView
        var layoutManager = LinearLayoutManager(context)
        rvRoom.adapter = myChatAdapter
        rvRoom.layoutManager = layoutManager
        rvRoom.setHasFixedSize(true)

        var chatList = ArrayList<MyChatRoomListItem>()

        VolleyService.myChatRoomListReq(UserInfo.NICKNAME, context!!, { success ->
            myChatAdapter!!.clear()

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
                    var chatAgree=json.getString("chat_agree")

                    if(category=="데이팅") {
                        var tempTitle=roomTitle.split("&")
                        if(tempTitle[0]==UserInfo.NICKNAME) roomTitle=tempTitle[1]
                        else roomTitle=tempTitle[0]
                    }

                    val ref = FirebaseDatabase.getInstance().reference.child("chat").child(roomId)
                    val query = ref.orderByChild("fulltime").limitToLast(1)

                    val childEventListener = object : ChildEventListener {
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
                        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                            chatConversation(p0)
                        }
                        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                            chatConversation(p0)
                        }
                        override fun onChildRemoved(p0: DataSnapshot) {}
                    }

                    query.addChildEventListener(childEventListener)

                    var lastChat = ""
                    var lastChatTime = ""

                    myChatAdapter!!.addItem(
                        roomId,
                        category,
                        maker,
                        roomTitle,
                        limitNum,
                        universityName,
                        curNum,
                        introduce,
                        lastChat,
                        lastChatTime,
                        chatAgree
                    )
                }
            }
            myChatAdapter!!.notifyDataSetChanged()
        })

        return view
    }

    fun chatConversation(dataSnapshot: DataSnapshot) {
        var i = dataSnapshot.children.iterator()

        var content = ((i.next() as DataSnapshot).getValue()) as String
        var fulltime = ((i.next() as DataSnapshot).getValue()) as String
        var roomId = ((i.next() as DataSnapshot).getValue()) as String
        var speaker=((i.next() as DataSnapshot).getValue()) as String
        var time=((i.next() as DataSnapshot).getValue()) as String
        myChatAdapter!!.insertLastChat(roomId,content,fulltime,time)
        myChatAdapter!!.sortByLastChat()
        myChatAdapter!!.notifyDataSetChanged()
    }
}
