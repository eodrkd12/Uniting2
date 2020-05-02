package com.example.commit.MainActivity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.commit.IntroActivity.LoginActivity
import com.example.commit.R
import com.example.commit.Class.UserInfo
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_dating_on_off.*
import kotlinx.android.synthetic.main.activity_join5.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

class SettingActivity : AppCompatActivity() {

    val PICK_FROM_CAMERA=0
    val PICK_FROM_ALBUM=1
    val CROP_FROM_CAMERA=2
    val CROP_FROM_ALBUM=3

    var imageCaptureUri: Uri?=null

    var dialog:Dialog? = null
    var dialogView: View? = null
    var dialogEditChange: EditText? = null
    var dialogBtnCheck: Button? = null
    var dialogTextCheck: TextView? = null
    var dialogBtnConfirm: Button? = null
    var dialogBtnCancel: Button? = null
    var nicknameTemp: String = ""
    var nicknameCheck: Int = 0
    var dialogimagechange_phone:Button?=null
    var dialogimagechange_cam:Button?=null

    var dialog_imageView:View?=null

    fun nicknameCheck() {
        VolleyService.nicknameCheckReq(dialogEditChange!!.text.toString(), this, {success->
            if(success==0)
            {
                dialogTextCheck!!.setText("중복된 닉네임입니다.")
                dialogTextCheck!!.setTextColor(Color.parseColor("#FF0000"))
            }
            else if(success==1)
            {
                VolleyService.checkTmpNickname(dialogEditChange!!.text.toString(), this, {success->
                    if(success==0)
                    {
                        dialogTextCheck!!.setText("중복된 닉네임입니다.")
                        dialogTextCheck!!.setTextColor(Color.parseColor("#FF0000"))
                    }
                    else if(success==1)
                    {
                        dialogTextCheck!!.setText("사용가능한 닉네임입니다.")
                        dialogTextCheck!!.setTextColor(Color.parseColor("#008000"))
                        VolleyService.insertTmpNickname(dialogEditChange!!.text.toString(), this, {success->})
                        nicknameTemp = dialogEditChange!!.text.toString()
                        nicknameCheck = 1
                    }
                })
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent=intent

        var tag=intent.getStringExtra("tag")

        dialog = Dialog(this)

        when(tag){
            "프로필 보기" ->{
                setContentView(R.layout.activity_profile)

                var background=this.getDrawable(R.drawable.image_profile)
                img_profile.background=background
                img_profile.clipToOutline=true

                var gender=""
                if(UserInfo.GENDER=="M"){
                    gender="남자"
                } else gender="여자"
                //text_id.text=UserInfo.ID
                text_nickname.text=UserInfo.NICKNAME
                text_name.text=UserInfo.NAME

                //현재 연도 구하기
                var calendar = GregorianCalendar(Locale.KOREA)
                var year = calendar.get(Calendar.YEAR)
                //이용자 생일 구하기
                var birthday = UserInfo.BIRTH
                birthday = birthday.substring(0, 4)
                //이용자 나이 계산
                var age = year - Integer.parseInt(birthday) + 1
                text_age.setText("${age}세")

                text_gender.text=gender
                text_department.text="${UserInfo.UNIV} ${UserInfo.DEPT}"

                text_department.text="계명대학교 컴퓨터공학전공"
                text_hobby.text="취미 : ${UserInfo.HOBBY}"
                text_personality.text="성격 : ${UserInfo.PERSONALITY}"

                VolleyService.getImageReq(UserInfo.NICKNAME,this, { success ->
                    Log.d("uniting",success!!.getString("user_image"))
                    val imageBytes = Base64.decode(success!!.getString("user_image"), 0)
                    val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                    img_profile.setImageBitmap(image)
                })

                text_imagechange.setOnClickListener({
                    dialog_imageView = layoutInflater.inflate(R.layout.dialog_imagechange, null)
                    dialogBtnCancel = dialog_imageView!!.findViewById<Button>(R.id.btn_imagechangecancel)
                    dialogimagechange_phone=dialog_imageView!!.findViewById<Button>(R.id.btn_changeimage_from_phone)
                    dialogimagechange_cam=dialog_imageView!!.findViewById<Button>(R.id.btn_changeimage_from_cam)

                    dialogimagechange_phone!!.setOnClickListener({
                        //이미지변경버튼
                        var albumIntent=Intent(Intent.ACTION_PICK)
                        albumIntent.setType(MediaStore.Images.Media.CONTENT_TYPE)
                        startActivityForResult(albumIntent,PICK_FROM_ALBUM)
                        dialog!!.dismiss()
                    })
                    dialogimagechange_cam!!.setOnClickListener({
                        var cameraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(cameraIntent,PICK_FROM_CAMERA)
                        dialog!!.dismiss()
                    })
                    dialogBtnCancel!!.setOnClickListener({
                        //취소버튼
                    })
                    dialog!!.setContentView(dialog_imageView)
                    dialog!!.show()

                })

                text_changenickname.setOnClickListener {
                    dialogView = layoutInflater.inflate(R.layout.dialog_changenickname, null)
                    dialogEditChange = dialogView!!.findViewById<EditText>(R.id.edit_change)
                    dialogBtnCheck = dialogView!!.findViewById<Button>(R.id.btn_changecheck)
                    dialogTextCheck = dialogView!!.findViewById<TextView>(R.id.text_changecheck)
                    dialogBtnConfirm = dialogView!!.findViewById<Button>(R.id.btn_changeconfirm)
                    dialogBtnCancel = dialogView!!.findViewById<Button>(R.id.btn_changecancel)

                    dialogBtnCheck!!.setOnClickListener {
                        if(dialogEditChange!!.text.toString().length < 3)
                        {
                            dialogTextCheck!!.text = "닉네임은 3자리 이상이어야 합니다."
                            dialogTextCheck!!.setTextColor(Color.parseColor("#FF0000"))
                        }
                        else
                        {
                            if(nicknameTemp == "")
                            {
                                nicknameCheck()
                            }
                            else if(nicknameTemp != dialogEditChange!!.text.toString())
                            {
                                VolleyService.deleteTmpNickname(nicknameTemp, this, {success->})
                                nicknameCheck()
                            }
                            else if(nicknameTemp == dialogEditChange!!.text.toString())
                            {
                                dialogTextCheck!!.setText("사용가능한 닉네임입니다.")
                                dialogTextCheck!!.setTextColor(Color.parseColor("#008000"))
                                nicknameCheck = 1
                            }
                        }
                    }

                    dialogBtnConfirm!!.setOnClickListener{
                        if(nicknameCheck == 1)
                        {
                            VolleyService.insertNickname(UserInfo.ID, nicknameTemp, this, {success->})
                            VolleyService.deleteTmpNickname(nicknameTemp, this, {success->})

                            var pref=this.getSharedPreferences("UserInfo",Context.MODE_PRIVATE)
                            var editor=pref.edit()
                            editor.remove("NICKNAME").commit()
                            editor.putString("NICKNAME",nicknameTemp).apply()
                            UserInfo.NICKNAME=nicknameTemp

                            Toast.makeText(this, "닉네임 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                            nicknameCheck = 0
                            nicknameTemp = ""
                            dialog!!.dismiss()
                        }
                        else
                        {
                            dialogTextCheck!!.text = "닉네임을 확인해주세요."
                            dialogTextCheck!!.setTextColor(Color.parseColor("#FF0000"))
                        }
                    }

                    dialogBtnCancel!!.setOnClickListener {
                        if(nicknameTemp != "")
                        {
                            VolleyService.deleteTmpNickname(nicknameTemp, this, {success->})
                        }
                        nicknameCheck = 0
                        nicknameTemp = ""
                        dialog!!.dismiss()
                    }

                    dialogEditChange!!.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(p0: Editable?) {

                        }

                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            dialogTextCheck!!.text = ""
                            nicknameCheck = 0
                        }
                    })

                    dialog!!.setContentView(dialogView)
                    dialog!!.show()
                }






            }
            "알림 설정"->{
                setContentView(R.layout.activity_alam_setting)
            }
            "문의하기"->{
                setContentView(R.layout.activity_ask)
            }
            "앱 버전"->{
                setContentView(R.layout.activity_app_version)
            }
            "공지사항"->{
                setContentView(R.layout.activity_notice)
            }
            "커뮤니티 이용규칙"->{
                setContentView(R.layout.activity_rule)
            }
            "개인정보 처리방침"->{
                setContentView(R.layout.activity_rule2)
            }
            "정보 수신 동의 "->{
                setContentView(R.layout.activity_agreement)
            }
            "회원 탈퇴"->{
                setContentView(R.layout.activity_withdrawal)
            }
            "로그아웃"->{
                var pref=this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                var editor=pref.edit()

                editor.clear()
                editor.commit()

                var intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data==null) {
            return
        }

        Log.d("uniting","onActivityResult")

        when(requestCode){
            PICK_FROM_CAMERA -> {
                Log.d("uniting","CAMERA")
                val imageBitmap = data!!.extras.get("data") as Bitmap
                img_profile.setImageBitmap(imageBitmap)
                var bitmap=((img_profile.drawable as Drawable) as BitmapDrawable).bitmap
                VolleyService.updateImageReq(UserInfo.NICKNAME,bitmap,this)

            }
            PICK_FROM_ALBUM -> {
                Log.d("uniting","ALBUM")
                imageCaptureUri=data!!.data

                try {
                    val imageBitmap= MediaStore.Images.Media.getBitmap(this.contentResolver,imageCaptureUri)
                    img_profile.setImageBitmap(imageBitmap)
                    var bitmap=((img_profile.drawable as Drawable) as BitmapDrawable).bitmap
                    VolleyService.updateImageReq(UserInfo.NICKNAME,bitmap,this)
                    
                } catch (e: FileNotFoundException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                } catch (e: IOException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }

            }
            CROP_FROM_CAMERA -> {

            }
            CROP_FROM_ALBUM -> {

            }
        }


    }

}
