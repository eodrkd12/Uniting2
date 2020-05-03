package com.example.commit.MainActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.ChatAdapter
import com.example.commit.Adapter.DrawerAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.Popup.ReportPopup
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_mainchat.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChatActivity : AppCompatActivity() {

    var chatAdapter = ChatAdapter()
    var roomId: String? = null
    var category: String? = null
    var title: String? = null
    var chatAgree: String? = null
    var maker: String? = null
    var userList = ArrayList<String>()

    var ref:DatabaseReference?=null
    var query:Query?=null

    lateinit var rv_user: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainchat)
        setSupportActionBar(main_layout_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        rv_user = findViewById(R.id.recycler_user)

        var intent = intent

        roomId = intent.getStringExtra("room_id")
        category = intent.getStringExtra("category")
        title = intent.getStringExtra("title")
        maker = intent.getStringExtra("maker")
        chatAgree = intent.getStringExtra("chat_agree")

        /*edit_chat.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus == true) {

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

                            VolleyService.sendFCMReq(
                                roomId!!,
                                title!!,
                                "${UserInfo.NICKNAME} : ${edit_chat.text}",
                                this
                            )

                            edit_chat!!.setText("")
                            list_chat.setSelection(chatAdapter.count - 1)
                        }
                    }
                })

            }
        }*/
    }

    override fun onResume() {
        super.onResume()

        main_layout_toolbar.title = title

        if(chatAgree=="true"){
            edit_chat.isEnabled=true
            btn_send.isEnabled=true
            layout_agree.visibility= View.GONE
            text_waiting.visibility=View.GONE
            //FCM 주제구독
            FirebaseMessaging.getInstance().subscribeToTopic(roomId!!)
                .addOnCompleteListener {
                    var msg = "${roomId} subscribe success"
                    if (!it.isSuccessful) msg = "${roomId} subscribe fail"
                }

            setChatting()
        }
        else if(chatAgree=="false"){
            if(maker==UserInfo.NICKNAME) {
                text_waiting.visibility = View.VISIBLE
                layout_agree.visibility=View.GONE
            }
            else {
                Log.d("uniting",maker)

                text_waiting.visibility=View.GONE
                layout_agree.visibility = View.VISIBLE
                text_agree.text="${maker}님이 대화를 요청하였습니다"
            }
            edit_chat.isEnabled=false
            btn_send.isEnabled=false
        }

        btn_agree.setOnClickListener {
            VolleyService.chatAgreeReq(roomId, this, { success ->
                VolleyService.sendFCMReq(
                    roomId!!,
                    "CHAT_AGREE",
                    "${UserInfo.NICKNAME}님이 대화 요청을 수락하였습니다",
                    this
                )

                layout_agree.visibility=View.GONE
                edit_chat.isEnabled=true
                btn_send.isEnabled=true
            })

            //FCM 주제구독
            FirebaseMessaging.getInstance().subscribeToTopic(roomId!!)
                .addOnCompleteListener {
                    var msg = "${roomId} subscribe success"
                    if (!it.isSuccessful) msg = "${roomId} subscribe fail"
                }

            setChatting()
        }

        btn_disagree.setOnClickListener {
            VolleyService.exitReq(UserInfo.NICKNAME, roomId!!, this, { success ->
                VolleyService.sendFCMReq(
                    roomId!!,
                    "CHAT_DISAGREE",
                    "${UserInfo.NICKNAME}님이 대화 요청을 거부하였습니다",
                    this
                )
                FirebaseMessaging.getInstance().unsubscribeFromTopic(roomId!!)
                finish()
            })
        }

    }

    fun setChatting(){
        list_chat.adapter=chatAdapter

        VolleyService.getJoinTimeReq(roomId!!, UserInfo.NICKNAME, this, { success ->
            ref = FirebaseDatabase.getInstance().reference.child("chat").child(roomId!!)
            query = ref!!.orderByChild("fulltime").startAt(success, "fulltime")

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

            query!!.addChildEventListener(childEventListener)

            btn_send.setOnClickListener {
                if (edit_chat.text.toString() != "") {

                    var map = HashMap<String, Any>()

                    val key: String? = ref!!.push().key

                    ref!!.updateChildren(map)

                    var root = ref!!.child(key!!)
                    var objectMap = HashMap<String, Any>()

                    val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                    val noon = current.format(DateTimeFormatter.ofPattern("a"))
                    var formatter: DateTimeFormatter? = null

                    if (noon == "오후")
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

    override fun onBackPressed() {
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        var inflater = getMenuInflater()
        inflater.inflate(R.menu.menu_chat, menu)
        menu!!.add(0, 0, 0, "메뉴").setIcon(R.drawable.option1_icon)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        /* menu!!.add("참여중인 유저").setEnabled(false)

         menu.add(UserInfo.NICKNAME)

         VolleyService.getUserInRoom(roomId!!,UserInfo.NICKNAME,this,{success ->
             for(i in 0..success!!.length()-1){
                 var jsonObject=success.getJSONObject(i)
                 var nickname=jsonObject.getString("user_nickname")
                 var item=menu.add(nickname)
                 item.setOnMenuItemClickListener {
                     var intent=Intent(this, ReportPopup::class.java)
                     intent.putExtra("nickname",nickname)
                     startActivity(intent)
                     true
                 }
             }

             var exit=menu.add("나가기")
             exit.setIcon(R.drawable.icon_exit_room)

             exit.setOnMenuItemClickListener {
                 VolleyService.exitReq(UserInfo.NICKNAME,roomId!!,this,{success -> })

                /* if(category=="데이팅")
                     VolleyService.datingExitReq(UserInfo.NICKNAME,this)

                 VolleyService.updateFCMGroupReq("remove",notificationKey!!,UserInfo.FCM_TOKEN,roomId!!,this)
                 var roomPref=this.getSharedPreferences("Room",Context.MODE_PRIVATE)
                 var editor=roomPref.edit()
                 var stringSet=roomPref.getStringSet("notification_key",null)
                 if(stringSet==null){
                     Log.d("test","방 나가기 오류 : Preference에 데이터가 없습니다.")
                 }
                 else{
                     stringSet.remove(notificationKey)
                     editor.clear().commit()
                     editor.putStringSet("notification_key",stringSet).apply()
                 }
 */
                 var intent=Intent(this,MainActivity::class.java)
                 startActivity(intent)

                 true
             }
         })*/
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            0 -> { // 메뉴 버튼
                userList.clear()
                main_layout.openDrawer(GravityCompat.END)    // 네비게이션 드로어 열기
                VolleyService.getUserInRoom(roomId!!, UserInfo.NICKNAME, this, { success ->
                    for (i in 0..success!!.length() - 1) {
                        var jsonObject = success.getJSONObject(i)
                        var nickname = jsonObject.getString("user_nickname")
                        userList.add(nickname)
                        Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();

                    }
                    rv_user.setHasFixedSize(true)
                    rv_user.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    rv_user.adapter = DrawerAdapter(userList!!)

                    main_layout.openDrawer(GravityCompat.END)

                    exit_button.setOnClickListener({
                        VolleyService.exitReq(
                            UserInfo.NICKNAME,
                            roomId!!,
                            this,
                            { success -> })
                        finish()
                    })


                })
            }


        }
        return false
    }
}