package com.example.commit.Fragment

import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.Volley
import com.example.commit.Class.UserInfo
import com.example.commit.MainActivity.ChatActivity

import com.example.commit.MainActivity.DatingActivity
import com.example.commit.MainActivity.MainActivity
import com.example.commit.MainActivity.OpenChatListActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.backgroundDrawable
import org.json.JSONObject
import java.util.*

class HomeFragment() : Fragment() {

    var textPartnerState:TextView? = null
    var textPartnerNull:TextView? = null
    var layoutProfile:ConstraintLayout? = null
    var imgProfile:ImageView? = null
    var textNickname:TextView? = null
    var textAge:TextView? = null
    var textDepartment:TextView? = null
    var textHobby:TextView? = null
    var textPersonality:TextView? = null
    var cardPartner:CardView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)


        var btnDating = view.findViewById<Button>(R.id.btn_dating)
        var btnOpen = view.findViewById<Button>(R.id.btn_open)

        textPartnerState = view.findViewById<TextView>(R.id.text_partner_state)
        textPartnerNull = view.findViewById<TextView>(R.id.text_partner_null)

        layoutProfile = view.findViewById<ConstraintLayout>(R.id.layout_profile)
        imgProfile = view.findViewById<ImageView>(R.id.img_profile)
        textNickname = view.findViewById<TextView>(R.id.text_nickname)
        textAge = view.findViewById<TextView>(R.id.text_age)
        textDepartment = view.findViewById<TextView>(R.id.text_department)
        textHobby = view.findViewById<TextView>(R.id.text_hobby)
        textPersonality = view.findViewById<TextView>(R.id.text_personality)
        cardPartner = view.findViewById<CardView>(R.id.card_partner)




        btnDating.setOnClickListener {
            var intent = Intent(activity, DatingActivity::class.java)
            startActivity(intent)
        }
        btnOpen.setOnClickListener {
            val intent = Intent(activity, OpenChatListActivity::class.java)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return view
    }

    override fun onResume() {
        super.onResume()

        textPartnerNull!!.visibility = View.GONE
        layoutProfile!!.visibility = View.GONE

        VolleyService.getJoinDating(UserInfo.NICKNAME, activity!!.applicationContext, { success ->
            if (success == null) {
                //데이팅 기능이 꺼져있는 경우
                textPartnerState!!.setText("만남 기능이 Off 상태입니다")
                textPartnerNull!!.visibility = View.VISIBLE
            } else {
                layoutProfile!!.visibility = View.VISIBLE

                //if(success.getString("joined")=="false"){
                textPartnerState!!.setText("랜덤 추천 상대")
                VolleyService.datingUserReq(
                    UserInfo.NICKNAME,
                    UserInfo.GENDER,
                    UserInfo.UNIV,
                    activity!!.applicationContext,
                    { success ->
                        var array = success

                        if (array!!.length() == 0) {
                            layoutProfile!!.visibility = View.GONE
                            textPartnerNull!!.visibility = View.VISIBLE
                            textPartnerNull!!.setText("대화 할 수 있는 상대가 없습니다")
                        } else {
                            val random = Random()
                            var num = random.nextInt(array!!.length())
                            var json = array[num] as JSONObject

                            textNickname!!.setText("${json.getString("user_nickname")}")
                            var nickname = json.getString("user_nickname")
                            //현재 연도 구하기
                            var calendar = GregorianCalendar(Locale.KOREA)
                            var year = calendar.get(Calendar.YEAR)
                            //이용자 생일 구하기
                            var birthday = json.getString("user_birthday")
                            birthday = birthday.substring(0, 4)
                            //이용자 나이 계산
                            var age = year - Integer.parseInt(birthday) + 1

                            textAge!!.setText("${age}세")

                            textDepartment!!.setText("${json.getString("dept_name")}")
                            textHobby!!.setText("취미 : ${json.getString("user_hobby")}")
                            textPersonality!!.setText("성격 : ${json.getString("user_personality")}")

                            VolleyService.getImageReq(
                                json.getString("user_nickname"),
                                activity!!.applicationContext,
                                { success ->
                                    val imageBytes =
                                        Base64.decode(success!!.getString("user_image"), 0)
                                    val image = BitmapFactory.decodeByteArray(
                                        imageBytes,
                                        0,
                                        imageBytes.size
                                    )

                                    imgProfile!!.setImageBitmap(image)
                                })

                            cardPartner!!.setOnClickListener {
                                val builder =
                                    AlertDialog.Builder(activity)
                                builder.setTitle("${nickname}님과의 대화")
                                builder.setMessage("시작하시겠습니까?")

                                builder.setPositiveButton("확인") { _, _ ->
                                    VolleyService.createChatRoomReq(
                                        UserInfo.NICKNAME,
                                        nickname,
                                        "",
                                        "데이팅",
                                        UserInfo.UNIV,
                                        activity!!.applicationContext,
                                        { success ->
                                            var roomId = success!!.getString("room_id")
                                            var intent = Intent(
                                                activity!!.applicationContext,
                                                ChatActivity::class.java
                                            )
                                            intent.putExtra("room_id", roomId)
                                            intent.putExtra(
                                                "title",
                                                "${nickname}"
                                            )
                                            intent.putExtra("category", "데이팅")
                                            intent.putExtra("chat_agree","false")
                                            intent.putExtra("maker",UserInfo.NICKNAME)
                                            //FCM 주제구독
                                            FirebaseMessaging.getInstance().subscribeToTopic(roomId)
                                                .addOnCompleteListener {
                                                    var msg = "${roomId} subscribe success"
                                                    if (!it.isSuccessful) msg =
                                                        "${roomId} subscribe fail"
                                                    ContextCompat.startActivity(
                                                        activity!!.applicationContext,
                                                        intent,
                                                        null
                                                    )
                                                }

                                            VolleyService.sendFCMReq(
                                                roomId!!,
                                                "CHAT_REQUEST",
                                                "${UserInfo.NICKNAME}님이 대화를 요청하였습니다.",
                                                context!!
                                            )
                                        })
                                }
                                builder.setNegativeButton("취소") { _, _ ->

                                }
                                builder.show()
                            }
                        }
                    })
            }
        })
    }
}
