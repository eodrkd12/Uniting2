package com.example.commit.MainActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.Volley
import com.example.commit.Adapter.ChatAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.Popup.ReportPopup
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_chat.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.HashMap

class ChatActivity : AppCompatActivity() {

    var chatAdapter = ChatAdapter()
    var roomId: String? = null
    var category: String? = null
    var title: String? = null
    var chatAgree:String?=null
    var maker:String?=null
    var notificationKey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        var intent = intent

        roomId = intent.getStringExtra("room_id")
        category = intent.getStringExtra("category")
        title = intent.getStringExtra("title")
        chatAgree=intent.getStringExtra("chat_agree")
        maker=intent.getStringExtra("maker")

        btn_agree.setOnClickListener {
            VolleyService.chatAgreeReq(roomId,this,{success ->
                VolleyService.sendFCMReq(roomId!!,"요청 수락","${UserInfo.NICKNAME}님이 대화 요청을 수락하였습니다",this)
            })
        }
        btn_disagree.setOnClickListener {
            VolleyService.exitReq(UserInfo.NICKNAME, roomId!!, this, { success ->
                VolleyService.sendFCMReq(roomId!!,"요청 수락","${UserInfo.NICKNAME}님이 대화 요청을 거부하였습니다",this)
                FirebaseMessaging.getInstance().unsubscribeFromTopic(roomId!!)
            })
        }

        if(chatAgree=="true"){
            edit_chat.isEnabled=true
            layout_agree.visibility== View.GONE
            text_waiting.visibility==View.GONE
            //FCM 주제구독
            FirebaseMessaging.getInstance().subscribeToTopic(roomId!!)
                .addOnCompleteListener {
                    var msg = "${roomId} subscribe success"
                    if (!it.isSuccessful) msg = "${roomId} subscribe fail"
                    Log.d("uniting", "ChatActivity.msg : ${UserInfo.NICKNAME} ${msg}")
                }

            toolbar.title = title

            list_chat.adapter = chatAdapter

            VolleyService.getJoinTimeReq(roomId!!, UserInfo.NICKNAME, this, { success ->
                val ref = FirebaseDatabase.getInstance().reference.child("chat").child(roomId!!)
                val query = ref.orderByChild("fulltime").startAt(success, "fulltime")

                val childEventListener = object : ChildEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    }

                    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                        chatConversation(p0)
                    }

                    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                        chatConversation(p0)
                    }

                    override fun onChildRemoved(p0: DataSnapshot) {
                    }
                }

                query.addChildEventListener(childEventListener)

                btn_send.setOnClickListener {
                    if (edit_chat.text.toString() != "") {

                        var map = HashMap<String, Any>()

                        val key: String? = ref.push().key

                        ref.updateChildren(map)

                        var root = ref.child(key!!)
                        var objectMap = HashMap<String, Any>()

                        val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                        val noon = current.format(DateTimeFormatter.ofPattern("a"))
                        var formatter: DateTimeFormatter? = null
                        if (noon == "PM")
                            formatter = DateTimeFormatter.ofPattern("오후 hh:mm")
                        else
                            formatter = DateTimeFormatter.ofPattern("오전 hh:mm")
                        val formatted = current.format(formatter)
                        val fulltime =
                            current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

                        objectMap.put("room_id", roomId!!)
                        objectMap.put("speaker", UserInfo.NICKNAME)
                        objectMap.put("content", edit_chat.text.toString())
                        objectMap.put("time", formatted)
                        objectMap.put("fulltime", fulltime)

                        root.updateChildren(objectMap)

                        var sendTitle = ""
                        if (category == "데이팅")
                            sendTitle = UserInfo.NICKNAME
                        else
                            sendTitle = title!!
                        VolleyService.sendFCMReq(
                            roomId!!,
                            sendTitle,
                            "${UserInfo.NICKNAME} : ${edit_chat.text}",
                            this
                        )

                        edit_chat!!.setText("")
                        list_chat.setSelection(chatAdapter.count - 1)
                    }

                }
            })
        }
        else{
            edit_chat.isEnabled=false
            if(UserInfo.NICKNAME==maker){
                text_waiting.visibility=View.VISIBLE
                layout_agree.visibility=View.GONE
            }
            else{
                text_waiting.visibility=View.GONE
                layout_agree.visibility=View.VISIBLE
            }
        }


    }

    override fun onBackPressed() {
        finish()
    }

    fun chatConversation(dataSnapshot: DataSnapshot) {
        var i = dataSnapshot.children.iterator()

        while (i.hasNext()) {

            var content = ((i.next() as DataSnapshot).getValue()) as String
            var fulltime = ((i.next() as DataSnapshot).getValue()) as String
            var roomId = ((i.next() as DataSnapshot).getValue()) as String
            var speaker = ((i.next() as DataSnapshot).getValue()) as String
            var time = ((i.next() as DataSnapshot).getValue()) as String

            chatAdapter.addItem(roomId, speaker, content, time, fulltime)
        }
        chatAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = getMenuInflater()
        inflater.inflate(R.menu.menu_chat, menu)


        menu!!.add("참여중인 유저").setEnabled(false)

        menu.add(UserInfo.NICKNAME)

        VolleyService.getUserInRoom(roomId!!, UserInfo.NICKNAME, this, { success ->
            for (i in 0..success!!.length() - 1) {
                var jsonObject = success.getJSONObject(i)
                var nickname = jsonObject.getString("user_nickname")
                var item = menu.add(nickname)
                item.setOnMenuItemClickListener {
                    var intent = Intent(this, ReportPopup::class.java)
                    intent.putExtra("nickname", nickname)
                    startActivity(intent)
                    true
                }
            }

            var exit = menu.add("나가기")
            exit.setIcon(R.drawable.icon_exit_room)
            exit.setOnMenuItemClickListener {
                VolleyService.exitReq(UserInfo.NICKNAME, roomId!!, this, { success -> })

                if (category == "데이팅")
                    VolleyService.datingExitReq(UserInfo.NICKNAME, this)

                VolleyService.updateFCMGroupReq(
                    "remove",
                    notificationKey!!,
                    UserInfo.FCM_TOKEN,
                    roomId!!,
                    this
                )
                var roomPref = this.getSharedPreferences("Room", Context.MODE_PRIVATE)
                var editor = roomPref.edit()
                var stringSet = roomPref.getStringSet("notification_key", null)
                if (stringSet == null) {
                    Log.d("test", "방 나가기 오류 : Preference에 데이터가 없습니다.")
                } else {
                    stringSet.remove(notificationKey)
                    editor.clear().commit()
                    editor.putStringSet("notification_key", stringSet).apply()
                }

                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        /*var title=item!!.title.toString()
        when(title){
            "exit_room" -> {
                VolleyService.exitReq(UserInfo.NICKNAME,roomId!!,this,{success ->

                })

                if(category=="데이팅") {
                    VolleyService.datingExitReq(UserInfo.NICKNAME,this)
                }

                var intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
        */
        return true
    }
}