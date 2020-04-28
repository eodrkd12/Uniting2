package com.example.commit.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.commit.Class.UserInfo
import org.json.JSONObject
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import kotlinx.android.synthetic.main.activity_image_test.*


class ImageTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.commit.R.layout.activity_image_test)

        var url="http://52.78.27.41:1901/user/getImage"

        var json= JSONObject()

        json.put("id", UserInfo.ID)

        var request=object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
                var stringImage=it.getString("user_image")
                var bitmap=StringToBitmap(stringImage)
                Log.d("test","${bitmap}")
                imageView.setImageBitmap(bitmap)
            },
            Response.ErrorListener {

            }){

        }

        Volley.newRequestQueue(this).add(request)
    }

    fun StringToBitmap(encodedString: String): Bitmap? {
        try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            return null
        }
    }

}
