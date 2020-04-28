package com.example.commit.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.ListItem.MyChatRoomListItem
import com.example.commit.MainActivity.ChatActivity
import com.example.commit.R

class MyChatListAdapter(val context:Context) : RecyclerView.Adapter<MyChatListAdapter.Holder>() {
    private var myChatRoomList = ArrayList<MyChatRoomListItem>()

    var isMyChat:Boolean=true
    var chatIdx:Int=0;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_my_chat_room_list, parent, false)
        chatIdx++
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return myChatRoomList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(myChatRoomList[position], context)
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!){

        var textTitle=itemView?.findViewById(R.id.text_title) as TextView
        var textJoinNum=itemView?.findViewById(R.id.text_join_num) as TextView
        var cardRoom=itemView?.findViewById(R.id.card_room) as CardView
        var textLastChat=itemView?.findViewById(R.id.text_last_chat) as TextView
        var textLastChatTime=itemView?.findViewById(R.id.text_last_chat_time) as TextView
        fun bind(item: MyChatRoomListItem, context: Context){
            textTitle.text=item.roomTitle
            if(item.cateName=="데이팅")
                textJoinNum.text = ""
            else
                textJoinNum.text="${item.curNum}"
            if(item.chatAgree=="false"){
                textLastChat.text="채팅 수락 대기중입니다"
                textLastChatTime.text=""
            }
            else{
                textLastChat.text="${item.lastChat}"
                if(item.lastChatTime==null)
                    textLastChatTime.text=""
                else
                    textLastChatTime.text="${item.lastChatTime}"
            }

            cardRoom.setOnClickListener {
                var intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("room_id", item.roomId)
                intent.putExtra("category", item.cateName)
                intent.putExtra("title", item.roomTitle)
                intent.putExtra("chat_agree", item.chatAgree)
                intent.putExtra("maker",item.maker)
                ContextCompat.startActivity(context, intent, null)
            }

            textJoinNum.bringToFront()
            textTitle.bringToFront()
        }
    }

    fun addItem(roomId: String, cateName: String, maker:String,roomTitle:String,limitNum:Int,universityName:String,curNum:Int,introduce:String,lastChat:String,lastChatTime:String,chatAgree:String){
        val item= MyChatRoomListItem(roomId, cateName, maker, roomTitle, limitNum, universityName, curNum, introduce, lastChat, lastChatTime,chatAgree)
        myChatRoomList.add(item)
    }

    fun clear(){
        myChatRoomList.clear()
    }

    fun insertLastChat(roomId: String, content: String, fulltime: String, time:String) {
        for(i in 0..myChatRoomList.size-1){
            if(myChatRoomList[i].roomId==roomId){
                myChatRoomList[i].lastChat=content
                myChatRoomList[i].lastChatFullTime=fulltime
                myChatRoomList[i].lastChatTime=time
                break
            }
        }
    }

    fun sortByLastChat() {
        myChatRoomList.sortByDescending { selector(it) }
    }

    fun selector(room:MyChatRoomListItem): String = room.lastChatFullTime!!
}