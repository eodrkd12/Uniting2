package com.example.commit.MainActivity

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.MenuAdapter
import com.example.commit.Adapter.MenuPreviewAdapter
import com.example.commit.Adapter.ReviewAdapter
import com.example.commit.Adapter.ReviewPreviewAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.Menu
import com.example.commit.ListItem.ReviewItem
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import kotlinx.android.synthetic.main.activity_inform.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.wrapContent
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup

class InformActivity : AppCompatActivity(), OnMapReadyCallback {

    val ncclientId:String = "4fk2n2jfrm"
    val ncclientSecret:String = "Ov8qSzvnvb4K3D8WGfh5ZP9yojd0e5UJvBJazuH1"

    companion object {
        var mapx: Double? = null
        var mapy: Double? = null
        var phone: String? = null
        var name: String? = null
        var id:String? = null
        var roadAddr:String? = null
        var menuList = ArrayList<Menu>()
        var options:String? = null
        var bizHourInfo:String? = null
        var tags:String? = null
        var dialog:Dialog? = null
    }

    /*fun fetchJson(vararg p0: String) {
        val intent = intent
        val text = intent.getStringExtra("roadAddress")
        val apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=$text"

        val url = URL(apiURL)

        val request = Request.Builder()
            .url(url)
            .addHeader("X-NCP-APIGW-API-KEY-ID", ncclientId)
            .addHeader("X-NCP-APIGW-API-KEY", ncclientSecret)
            .method("GET", null)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {

                runOnUiThread {
                    val body = response?.body()?.string()
                    println("Success to execute request : $body")

                    val jObject : JSONObject = JSONObject(body)
                    val jArray = jObject.getJSONArray("addresses")

                    for(i in 0 until jArray.length())
                    {
                        val obj = jArray.getJSONObject(i)
                        mapx = obj.getDouble("x")
                        mapy = obj.getDouble("y")
                    }
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inform)



        var check:Boolean = true
        var sync = AsyncTask()

        var intent=intent
        name =intent.getStringExtra("name")
        mapx = intent.getStringExtra("x").toDouble()
        mapy = intent.getStringExtra("y").toDouble()
        phone = intent.getStringExtra("phone")
        id = intent.getStringExtra("id")
        roadAddr = intent.getStringExtra("roadAddr")
        options = intent.getStringExtra("options")
        bizHourInfo = intent.getStringExtra("bizHourInfo")
        tags = intent.getStringExtra("tags")


        sync.execute("")



        if(bizHourInfo == "" || bizHourInfo == null)
        {
            bizHourInfo = "정보없음"
        }

        if(options == "" || options == null)
        {
            options = "정보없음"
        }

        if(phone == "" || phone == null)
        {
            phone = "정보없음"
        }

        if("|" in bizHourInfo!!)
        {
            var index:Int =  bizHourInfo!!.indexOf("|")
            var timeOpenClose:String = bizHourInfo!!.substring(0, index-1)
            text_bizHourInfo.text = timeOpenClose + " ∨"
            var timeData:String = bizHourInfo!!.substring(index+2, bizHourInfo!!.length).replace(" | ", "\n")
            text_bizHourInfo.setOnClickListener{
                if(check == true)
                {
                    text_bizHourInfo.setText(timeOpenClose + " ∧\n" + timeData)

                    check = false
                }
                else
                {
                    text_bizHourInfo.text = timeOpenClose + " ∨"
                    check = true
                }
            }
        }
        else
        {
            text_bizHourInfo.text = bizHourInfo
        }


        text_inform_title.text = name
        text_roadaddr.text = roadAddr
        text_phone.text = phone



        var reviewArray: JSONArray? = null
        var reviewList: ArrayList<ReviewItem> = arrayListOf()

        VolleyService.getReviewReq(name!!, UserInfo.UNIV, this, { success ->
            reviewList.clear()
            reviewArray = success

            if (reviewArray!!.length() == 0) {

            } else {
                for (i in 0..reviewArray!!.length() - 1) {
                    var json = JSONObject()
                    json = reviewArray!![i] as JSONObject

                    var nickname = json.getString("user_nickname")
                    var date = json.getString("date")
                    var point = json.getInt("point")
                    var content = json.getString("content")

                    var starpoint:String? = null

                    if(point==1)
                    {
                        starpoint = "★☆☆☆☆"
                    }
                    else if(point==2)
                    {
                        starpoint = "★★☆☆☆"
                    }
                    else if(point==3)
                    {
                        starpoint = "★★★☆☆"
                    }
                    else if(point==4)
                    {
                        starpoint = "★★★★☆"
                    }
                    else if(point==5)
                    {
                        starpoint = "★★★★★"
                    }

                    reviewList.add(ReviewItem(nickname, date, starpoint, content))
                }


                if(reviewList.size < 5)
                {
                    reviewRV.setHasFixedSize(true)
                    reviewRV.layoutManager = LinearLayoutManager(this)
                    reviewRV.adapter = ReviewPreviewAdapter(reviewList, reviewList.size)
                }
                else
                {
                    reviewRV.setHasFixedSize(true)
                    reviewRV.layoutManager = LinearLayoutManager(this)
                    reviewRV.adapter = ReviewPreviewAdapter(reviewList, 5)
                }


            }
        })

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)


        text_phone.setOnClickListener {
            val builder = AlertDialog.Builder(ContextThemeWrapper(this@InformActivity, R.style.Theme_AppCompat_Light_Dialog))
            builder.setTitle(name)
            builder.setMessage(phone)

            builder.setNegativeButton("통화") { dialog, id->
                Log.d("test", phone)
                val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phone}"))
                try {
                    startActivity(call)
                } catch (e: Exception) {
                    Log.d("test",e.toString())
                    e.printStackTrace()
                }
            }
            builder.setPositiveButton("취소") { dialog, id ->

            }
            builder.show()
        }

        rating_inform.setOnRatingBarChangeListener{ ratingBar, fl, b ->
            if(rating_inform.rating != 0.0f) {
                val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar)
                val dialogView = layoutInflater.inflate(R.layout.dialog_insertreview, null)
                val dialogContent = dialogView.findViewById<EditText>(R.id.edit_insertreview)
                val dialogRatingBar = dialogView.findViewById<RatingBar>(R.id.rating_review)
                val dialogButton = dialogView.findViewById<Button>(R.id.btn_insertreview)

                dialogRatingBar.setRating(ratingBar.rating)

                dialog.getWindow().getAttributes().windowAnimations = R.style.AnimationPopupStyle
                dialog.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))
                dialog.show()
                rating_inform.setRating(0.0f)
                //rating_inform.setRating(num.toFloat())

                dialogButton.setOnClickListener{
                    VolleyService.insertReviewReq(UserInfo.NICKNAME, name!!, UserInfo.UNIV, dialogRatingBar.rating.toInt(), dialogContent.text.toString(), this,{ success ->
                    })
                    dialog.dismiss()
                }
            }
        }

        text_viewmenu.setOnClickListener{
            var intent = Intent(this, MenuActivity::class.java)
            //intent.putParcelableArrayListExtra("menu", menu)
            intent.putExtra("menuList", menuList)
            startActivity(intent)
        }

        text_viewreview.setOnClickListener{
            var intent = Intent(this, ReviewActivity::class.java)
            intent.putExtra("reviewList", reviewList)
            startActivity(intent)
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(mapy!!,mapx!!))
        val zoomUpdate = CameraUpdate.zoomTo(18.0)
        naverMap.moveCamera(cameraUpdate)
        naverMap.moveCamera(zoomUpdate)

        val marker = Marker()
        marker.position = LatLng(mapy!!, mapx!!)
        marker.map = naverMap
    }

    inner class AsyncTask: android.os.AsyncTask<String, Long, ArrayList<Menu>>() {
        override fun onPreExecute() {
            super.onPreExecute()
            dialog = Dialog(this@InformActivity, R.style.loading_dialog_style)
            var pb = ProgressBar(this@InformActivity)

            dialog!!.addContentView(pb, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
            dialog!!.show()
        }

        override fun doInBackground(vararg p0: String?): ArrayList<Menu> {
            val url = "https://store.naver.com/restaurants/detail?id=$id"
            try {
                val doc = Jsoup.connect(url).get()
                val menuData = doc.select("ul[class=list_menu]").select("li")
                //timeData = doc.select("div.biztime > span").text()
                menuList.clear()
                menuData.forEachIndexed { index, element ->
                    val menuName = element.select("li span[class=name]").text()
                    val menuPrice = element.select("li em[class=price]").text()
                    menuList?.add(Menu(menuName, menuPrice))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return menuList!!
        }

        override fun onProgressUpdate(vararg values: Long?) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: ArrayList<Menu>?) {
            super.onPostExecute(result)

            if(menuList.size < 5)
            {
                menuRV.adapter = MenuPreviewAdapter(result!!, menuList.size-1)
                menuRV.setHasFixedSize(true)
                menuRV.layoutManager = LinearLayoutManager(this@InformActivity, RecyclerView.VERTICAL, false)
            }
            else
            {
                menuRV.adapter = MenuPreviewAdapter(result!!, 5)
                menuRV.setHasFixedSize(true)
                menuRV.layoutManager = LinearLayoutManager(this@InformActivity, RecyclerView.VERTICAL, false)
            }

            dialog!!.dismiss()
        }


    }

}
