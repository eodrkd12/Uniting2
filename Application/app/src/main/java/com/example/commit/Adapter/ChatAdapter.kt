package com.example.commit.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.ChatItem
import com.example.commit.ListItem.DatingItem
import com.example.commit.MainActivity.ChatActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import org.jetbrains.annotations.NotNull

/*class ChatAdapter(val context:Context) : RecyclerView.Adapter<ChatAdapter.Holder>(){

    private var chatList = ArrayList<ChatItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return chatList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(chatList[position], context)
    }

    inner class Holder(itemView:View?): RecyclerView.ViewHolder(itemView!!){

        fun bind(item: ChatItem, context: Context){
            Log.d("test","${item.speaker} : ${item.content} ${item.time} ${item.fulltime}")
            if(!item.isMyChat!!) {
                var textSpeaker = itemView?.findViewById(R.id.text_speaker) as TextView
                textSpeaker.text=item.speaker

                var textContent=itemView?.findViewById(R.id.text_content) as TextView
                textContent.text=item.content

                var textTime=itemView?.findViewById(R.id.text_time) as TextView
                textTime.text=item.time
            }
            else{
                var textMyContent=itemView?.findViewById(R.id.text_my_content) as TextView
                textMyContent.text=item.content

                var textMyTime=itemView?.findViewById(R.id.text_my_time) as TextView
                textMyTime.text=item.time
            }
        }
    }

    fun addItem(roomId: String, speaker: String, content: String, time: String,fulltime: String){
        val item = ChatItem()

        if (speaker == UserInfo.NICKNAME)
            item.isMyChat = true
        else
            item.isMyChat = false

        item.roomId = roomId
        item.speaker = speaker
        item.content = content
        item.time = time
        item.fulltime = fulltime

        chatList.add(item)
    }

    fun clear(){
        chatList.clear()
    }
}*/

class ChatAdapter : BaseAdapter() {
    private var chatList = ArrayList<ChatItem>()
    override fun getCount(): Int {
        return chatList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return chatList.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val context: Context? = parent?.context

        var item = chatList[position]

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        //if (view == null) {
            val inflater =
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            if(!item.isMyChat!!) {
                view = inflater.inflate(R.layout.item_chat, parent, false)
                var textSpeaker = view!!.findViewById(R.id.text_speaker) as TextView
                textSpeaker!!.text = item.speaker
                /*var imgSpeaker = view.findViewById(R.id.img_speaker) as ImageView
                imgSpeaker!!.setImageBitmap(ImageManager.StringToBitmap(item.stringImage!!))*/
                var textContent = view!!.findViewById(R.id.text_content) as TextView
                var textTime = view.findViewById(R.id.text_time) as TextView

                textContent.text = item.content
                textTime.text = item.time
            }
            else {
                view = inflater.inflate(R.layout.item_my_chat, parent, false)
                /*var textSpeaker=view!!.findViewById(R.id.text_speaker) as TextView
                textSpeaker!!.text="나"*/

                var textContent = view!!.findViewById(R.id.text_content) as TextView
                var textTime = view.findViewById(R.id.text_time) as TextView

                textContent.text = item.content
                textTime.text = item.time
            }
        //}



        return view
    }

    fun addItem(
        roomId: String, speaker: String, content: String, time: String, fulltime: String

    ) {
        val item = ChatItem()

        if (speaker == UserInfo.NICKNAME)
            item.isMyChat = true
        else
            item.isMyChat = false

        item.roomId = roomId
        item.speaker = speaker
        item.content = content
        item.time = time
        item.fulltime = fulltime
        //item.stringImage=stringImage

        chatList.add(item)
    }

    fun clear() {
        chatList.clear()
    }
}
