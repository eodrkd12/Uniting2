/*package com.example.commit.IntroActivity

//프사 설정은 나중에 ..

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_join5.*
import java.lang.Exception
import android.provider.MediaStore.Images
import java.io.*
import android.widget.Toast
import com.example.commit.R
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import android.R.attr.data
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.core.app.NotificationCompat.getExtras
import androidx.core.content.FileProvider
import com.example.commit.Singleton.VolleyService


class Join5Activity : AppCompatActivity() {

    val PICK_FROM_CAMERA=0
    val PICK_FROM_ALBUM=1
    val CROP_FROM_CAMERA=2
    val CROP_FROM_ALBUM=3

    var imageCaptureUri:Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join5)


        var getIntent=intent

        var id=getIntent.getStringExtra("id")
        var pw=getIntent.getStringExtra("pw")
        var name=getIntent.getStringExtra("name")
        var birthday=getIntent.getStringExtra("birthday")
        var gender=getIntent.getStringExtra("gender")
        var nickname=getIntent.getStringExtra("nickname")
        var webMail=getIntent.getStringExtra("univ_mail")
        var universityName=getIntent.getStringExtra("univ_name")
        var departmentName=getIntent.getStringExtra("dept_name")
        var enterYear=getIntent.getStringExtra("enter_year")


        image_profile.setOnClickListener {
            val builder =
                AlertDialog.Builder(this)
            builder.setTitle("프로필 사진 선택")

            builder.setPositiveButton("사진 촬영") { _, _ ->
                getImageFromCamera()
            }
            builder.setNegativeButton("앨범에서 선택") { _, _ ->
                getImageFromAlbum()
            }

            builder.setNeutralButton("취소"){_,_-> }


            builder.show()
        }

        btn_complete.setOnClickListener {
            var bitmap=(image_profile.drawable as BitmapDrawable).bitmap

            VolleyService.joinReq(id,pw,name,birthday,gender,nickname,webMail,universityName,departmentName,enterYear,bitmap,this,{ success->
                if(success.equals("success")) {
                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            })


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data==null) return

        when(requestCode){
            PICK_FROM_CAMERA -> {
                val imageBitmap = data!!.extras.get("data") as Bitmap
                image_profile.setImageBitmap(imageBitmap)
            }
            PICK_FROM_ALBUM -> {
                imageCaptureUri=data!!.data

                var bitmap: Bitmap? = null
                try {
                    bitmap=Images.Media.getBitmap(this.contentResolver,imageCaptureUri)
                } catch (e: FileNotFoundException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                } catch (e: IOException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }

                image_profile.setImageBitmap(bitmap)
            }
            CROP_FROM_CAMERA -> {

            }
            CROP_FROM_ALBUM -> {

            }
        }
    }

    fun getImageFromCamera():Unit{
        var cameraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent,PICK_FROM_CAMERA)
    }

    fun getImageFromAlbum():Unit{
        var albumIntent=Intent(Intent.ACTION_PICK)
        albumIntent.setType(Images.Media.CONTENT_TYPE)
        startActivityForResult(albumIntent,PICK_FROM_ALBUM)
    }


}*/
