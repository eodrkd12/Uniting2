package com.example.commit.Fragment

import android.app.DownloadManager
import android.content.ContentValues.TAG
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.CafeVerticalAdapter
import com.example.commit.R
import kotlinx.android.synthetic.main.fragment_cafeteria.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.Charset
import java.util.*
import java.util.Base64.Decoder
import kotlin.collections.ArrayList
import android.app.Activity
import android.app.Dialog
import android.content.ContentValues.TAG
import android.os.AsyncTask
import android.util.Log
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.commit.Adapter.CafeteriaAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.*
import com.example.commit.Singleton.VolleyService
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.cafeteria_horizontal.view.*
import java.io.IOException
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection

class CafeteriaFragment() : Fragment() {

    lateinit var CafeVerticalRV: RecyclerView
    lateinit var CafeHorizontalRV: RecyclerView

    companion object {
        var cafetype = arrayListOf<Type>(Type("한식"), Type("중식"), Type("일식"), Type("치킨"))
        var dialog: Dialog? = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_cafeteria, container, false)
        val rootView1 = inflater.inflate(R.layout.cafeteria_horizontal, container, false)

        CafeHorizontalRV = rootView1.findViewById(R.id.CafeHorizontalRV)
        CafeVerticalRV = rootView.findViewById(R.id.CafeVerticalRV)

        CafeVerticalRV.setHasFixedSize(true)
        CafeVerticalRV.layoutManager = LinearLayoutManager(activity)
        var adapter = CafeVerticalAdapter(activity!!, cafetype)
        adapter.notifyDataSetChanged()
        CafeVerticalRV.adapter = adapter

        /*fun fetchJson(vararg p0: String) {
            val searchtext = "성서계명대" + "한식"
            val text:String = URLEncoder.encode(searchtext, "UTF-8")
            val apiURL = "https://store.naver.com/sogum/api/businesses?start=1&display=10&query=$text&sortingOrder=reviewCount"

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

                    activity!!.runOnUiThread {
                        val body = response?.body()?.string()
                        println("Success to execute request : $body")

                        val gson = GsonBuilder().create()

                        val homefeed = gson.fromJson(body, Homefeed::class.java)

                        for(i in 0..homefeed.items.size-1) {
                            VolleyService.getReviewsScoreReq(homefeed.items.get(i).name!!, UserInfo.UNIV, activity!!, { success ->
                                var point:String? = null
                                point = success
                                if(point == "null")
                                {
                                    homefeed.items.get(i).starPoint = "★0"
                                }
                                else
                                {
                                    homefeed.items.get(i).starPoint = "★" + point
                                }
                            })
                        }

                        CafeVerticalRV.setHasFixedSize(true)
                        CafeVerticalRV.layoutManager = LinearLayoutManager(activity)
                        CafeVerticalRV.adapter = CafeVerticalAdapter(activity!!, homefeed, cafetype)
                    }
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    println("Failed to execute request")
                }
            })
        }*/



        /*fun fetchJson(vararg p0: String) {
            val text:String = URLEncoder.encode("계명대 맛집", "UTF-8")
            val apiURL = "https://store.naver.com/sogum/api/businesses?start=1&display=10&query=$text&sortingOrder=reviewCount"

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

                    activity!!.runOnUiThread {
                        val body = response?.body()?.string()
                        println("Success to execute request : $body")

                        val gson = GsonBuilder().create()

                        val homefeed = gson.fromJson(body, Homefeed::class.java)

                        for(i in 0..homefeed.items.size) {
                            if(homefeed.items[i].category?.contains("한식") == true)
                                koreanfood.add(KoreanFood(homefeed.items[i].name, homefeed.items[i].imageSrc))
                            else if(homefeed.items[i].category?.contains("중식") == true)
                                chinesefood.add(ChineseFood(homefeed.items[i].name, homefeed.items[i].imageSrc))
                            else if(homefeed.items[i].category?.contains("일식") == true)
                                japanesefood.add(JapaneseFood(homefeed.items[i].name, homefeed.items[i].imageSrc))
                        }
                    }
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    println("Failed to execute request")
                }
            })
        }
        fetchJson(" ")*/


        /*doAsync {
            val url = "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=%EA%B3%84%EB%AA%85%EB%8C%80%EC%8B%9D%EB%8B%B9"
            try {
                val doc = Jsoup.connect(url).get()
                val movieData = doc.select("div[class=list_area]").select("li")

                movieData.forEachIndexed { index, element ->


                    val mImgUrl = element.select("li div[class=thumb]").select("img").attr("src")

                    val list = Image(mImgUrl)
                    images.add(list)

                    activity!!.runOnUiThread {
                        CafeVerticalRV.setHasFixedSize(true)
                        CafeVerticalRV.layoutManager = LinearLayoutManager(activity)
                        CafeVerticalRV.adapter = CafeVerticalAdapter(activity!!)
                    }
                    Log.d(TAG, images.size.toString())

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }*/
        return rootView
    }
}
