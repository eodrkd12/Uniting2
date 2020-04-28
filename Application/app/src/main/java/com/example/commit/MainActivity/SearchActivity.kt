package com.example.commit.MainActivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.example.commit.Adapter.ChatRoomListAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.ChatRoomListItem
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_open_chat_list.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.btn_search
import org.json.JSONArray
import org.json.JSONObject

/*class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var searchArray: JSONArray? = null

        btn_search.setOnClickListener {
            if (text_search.text.toString() == "")
            {
                Toast.makeText(this, "검색어가 없습니다.", Toast.LENGTH_SHORT).show()
            }
            else
            {
              //  var chatRoomAdapter = ChatRoomListAdapter()
                var listChatRoom = search_chat_room
               // listChatRoom.adapter = chatRoomAdapter

                VolleyService.getSearchReq(UserInfo.UNIV, text_search.text.toString(), this, { success ->
                    var imm:InputMethodManager= getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(text_search.windowToken,0)

                  //  chatRoomAdapter.clear()
                    var searchArray = success

                    if (searchArray!!.length() == 0) {

                    } else {
                        for (i in 0..searchArray!!.length() - 1) {
                            var json = searchArray[i] as JSONObject
                            var roomId = json.getString("room_id")
                            var category = json.getString("cate_name")
                            var maker = json.getString("maker")
                            var roomTitle = json.getString("room_title")
                            var limitNum = json.getInt("limit_num")
                            var universityName = json.getString("univ_name")
                            var curNum = json.getInt("cur_num")
                            var introduce = json.getString("introduce")

                           /* chatRoomAdapter.addItem(
                                roomId,
                                category,
                                maker,
                                roomTitle,
                                limitNum,
                                universityName,
                                curNum,
                                introduce
                            )*/
                        }
                    }
                })
            }
        }
    }
}*/
