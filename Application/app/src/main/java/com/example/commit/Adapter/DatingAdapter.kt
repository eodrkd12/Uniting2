package com.example.commit.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.DatingItem
import com.example.commit.MainActivity.ChatActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_chat.*

class DatingAdapter(val context: Context) : RecyclerView.Adapter<DatingAdapter.Holder>() {

    private var datingList = ArrayList<DatingItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_dating_list, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return datingList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(datingList[position], context)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var imageProfile = itemView?.findViewById(R.id.img_profile) as ImageView
        var textNickname = itemView?.findViewById(R.id.text_nickname) as TextView
        var textAge = itemView?.findViewById(R.id.text_age) as TextView
        var textDepartment = itemView?.findViewById(R.id.text_department) as TextView
        var textHobby = itemView?.findViewById(R.id.text_hobby) as TextView
        var textPersonality = itemView?.findViewById(R.id.text_personality) as TextView
        var cardPartner = itemView?.findViewById(R.id.card_partner) as View

        fun bind(item: DatingItem, context: Context) {
            VolleyService.getImageReq(item.nickname!!, context, { success ->
                val imageBytes = Base64.decode(success!!.getString("user_image"), 0)
                val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                imageProfile.setImageBitmap(image)
            })

            textNickname.text = "닉네임 : ${item.nickname}"
            textAge.text = "나이 : ${item.age}"
            textDepartment.text = "학과 : ${item.department}"
            textHobby.text = "취미 : ${item.hobby}"
            textPersonality.text = "성격 : ${item.personality}"
            imageProfile.bringToFront()
            cardPartner.setOnClickListener {
                val builder =
                    AlertDialog.Builder(context!!)
                builder.setTitle("${item.nickname}님과의 대화")
                builder.setMessage("시작하시겠습니까?")

                builder.setPositiveButton("확인") { _, _ ->
                    VolleyService.createChatRoomReq(
                        UserInfo.NICKNAME,
                        item.nickname!!,
                        "",
                        "데이팅",
                        UserInfo.UNIV,
                        context,
                        { success ->
                            var roomId = success!!.getString("room_id")
                            var intent = Intent(context, ChatActivity::class.java)
                            intent.putExtra("room_id", roomId)
                            intent.putExtra("title", "${item.nickname}")
                            intent.putExtra("category", "데이팅")
                            intent.putExtra("chat_agree", "false")
                            intent.putExtra("maker", UserInfo.NICKNAME)

                            //FCM 주제구독
                            FirebaseMessaging.getInstance().subscribeToTopic(roomId)
                                .addOnCompleteListener {
                                    var msg = "${roomId} subscribe success"
                                    if (!it.isSuccessful) msg = "${roomId} subscribe fail"
                                    Log.d("uniting", "DatingAdapter.msg : ${msg}")
                                    startActivity(context, intent, null)
                                }
                            //FCM 주제구독취소
                            /*FirebaseMessaging.getInstance().unsubscribeFromTopic(roomId)
                                .addOnCompleteListener {
                                    var msg="${roomId} unsubscribe success"
                                    if(!it.isSuccessful) msg="${roomId} unsubscribe fail"
                                    Log.d("uniting","DatingAdapter.VolleyService.createChatRoomReq msg : ${msg}")
                                }*/

                            VolleyService.sendFCMReq(
                                roomId!!,
                                "대화 요청",
                                "${UserInfo.NICKNAME}님이 대화를 요청하였습니다.",
                                context
                            )
                        })
                }
                builder.setNegativeButton("취소") { _, _ ->

                }
                builder.show()
            }

        }
    }

    fun addItem(
        nickname: String,
        department: String,
        age: Int,
        hobby: String,
        personality: String
    ) {
        val item = DatingItem()

        item.nickname = nickname
        item.department = department
        item.age = age.toString()
        item.hobby = hobby
        item.personality = personality

        datingList.add(item)
    }

    fun getNickname(position: Int): String? {
        var datingItem = datingList.get(position)

        return datingItem.nickname
    }

    fun clear() {
        datingList.clear()
    }
}