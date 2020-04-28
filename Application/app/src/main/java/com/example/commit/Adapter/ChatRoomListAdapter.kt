package com.example.commit.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.ChatRoomListItem
import com.example.commit.MainActivity.ChatActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService

class ChatRoomListAdapter(val context: Context) :
    RecyclerView.Adapter<ChatRoomListAdapter.Holder>() {

    private var chatRoomList = ArrayList<ChatRoomListItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chat_room_list, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return chatRoomList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(chatRoomList[position], context)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var textTitle = itemView?.findViewById(R.id.text_title) as TextView
        var textJoinNum = itemView?.findViewById(R.id.text_join_num) as TextView
        var cardRoom = itemView?.findViewById(R.id.card_room) as CardView

        fun bind(item: ChatRoomListItem, context: Context) {
            textTitle.text = item.roomTitle
            textJoinNum.text = "${item.curNum}명 참가중"


            cardRoom.setOnClickListener {
                VolleyService.checkJoinReq(item.roomId!!, UserInfo.NICKNAME, context, { success ->
                    if (success == "true") {
                        val builder =
                            AlertDialog.Builder(context!!)
                        builder.setTitle("참여 중인 방입니다.")

                        builder.setPositiveButton("입장") { _, _ ->
                            var intent = Intent(context, ChatActivity::class.java)
                            intent.putExtra("room_id", item.roomId)
                            intent.putExtra("category", item.cateName)
                            intent.putExtra("title", item.roomTitle)
                            startActivity(context, intent, null)
                        }
                        builder.setNegativeButton("취소") { _, _ ->

                        }
                        builder.show()
                    } else {
                        if (item.curNum == item.limitNum) {
                            val builder =
                                AlertDialog.Builder(context!!)
                            builder.setTitle("방이 가득찼습니다.")
                            builder.setPositiveButton("확인") { _, _ ->

                            }
                            builder.show()
                        } else {
                            var builder = AlertDialog.Builder(context!!)
                            builder.setTitle("방에 입장하시겠습니까?")
                            builder.setMessage(item.introduce)
                            builder.setPositiveButton("입장") { _, _ ->
                                VolleyService.joinChatRoomReq(
                                    item.roomId!!,
                                    UserInfo.NICKNAME,
                                    context,
                                    { success ->
                                        if (success == 1) {
                                            var intent = Intent(context, ChatActivity::class.java)
                                            intent.putExtra("room_id", item.roomId)
                                            intent.putExtra("category", item.cateName)
                                            intent.putExtra("title", item.roomTitle)
                                            startActivity(context, intent, null)
                                        }
                                    })
                            }
                            builder.setNegativeButton("취소") { _, _ ->

                            }
                            builder.show()
                        }
                    }
                })
            }

            textJoinNum.bringToFront()
            textTitle.bringToFront()
        }
    }

    fun addItem(
        roomId: String,
        cateName: String,
        maker: String,
        roomTitle: String,
        limitNum: Int,
        universityName: String,
        curNum: Int,
        introduce: String
    ) {
        val item = ChatRoomListItem()

        item.roomId = roomId
        item.cateName = cateName
        item.maker = maker
        item.roomTitle = roomTitle
        item.limitNum = limitNum
        item.universityName = universityName
        item.curNum = curNum
        item.introduce = introduce

        chatRoomList.add(item)
    }

    fun clear() {
        chatRoomList.clear()
    }
}