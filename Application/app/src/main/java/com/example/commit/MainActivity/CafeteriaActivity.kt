package com.example.commit.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commit.Adapter.CafeteriaAdapter
import com.example.commit.Adapter.CafeteriaGridAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.Homefeed
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_cafeteria.*
import okhttp3.*
import org.jetbrains.anko.doAsync
import java.io.IOException
import java.net.URL
import java.net.URLEncoder

class CafeteriaActivity : AppCompatActivity() {

    companion object{
        var cafetype:String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafeteria)
        val intent = intent
        cafetype = intent.getStringExtra("cafetype")
        doAsync {
            fetchJson(" ")
        }

        text_category.text= cafetype
    }

    fun fetchJson(vararg p0: String) {
        val searchtext = "성서계명대" + cafetype
        val text:String = URLEncoder.encode(searchtext, "UTF-8")
        val apiURL = "https://store.naver.com/sogum/api/businesses?start=1&display=30&query=$text&sortingOrder=reviewCount"

        val url = URL(apiURL)

        val request = Request.Builder()
            .url(url)
            //.addHeader("X-Naver-Client-Id", clientId)
            //.addHeader("X-Naver-Client-Secret", clientSecret)
            .method("GET", null)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {

               runOnUiThread {
                    val body = response?.body()?.string()
                    println("Success to execute request : $body")

                    val gson = GsonBuilder().create()

                    val homefeed = gson.fromJson(body, Homefeed::class.java)


                    cafeteriaRV.setHasFixedSize(true)
                    cafeteriaRV.layoutManager = GridLayoutManager(this@CafeteriaActivity, 2)
                    cafeteriaRV.adapter = CafeteriaGridAdapter(this@CafeteriaActivity, homefeed)
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })
    }
}
