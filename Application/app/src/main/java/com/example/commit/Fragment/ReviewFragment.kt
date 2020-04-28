package com.example.commit.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commit.Adapter.ReviewAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.ReviewItem
import com.example.commit.MainActivity.InformActivity

import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_review.*
import kotlinx.coroutines.delay
import org.json.JSONArray
import org.json.JSONObject


class ReviewFragment(val name: String) : Fragment() {
    //lateinit var starpoint:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var reviewArray: JSONArray? = null
        var reviewList: ArrayList<ReviewItem> = arrayListOf()

        VolleyService.getReviewReq(name, UserInfo.UNIV, activity!!, { success ->
            reviewArray = success

            if (reviewArray!!.length() == 0) {
                text_review.visibility = View.VISIBLE
                /*btn_write.setOnClickListener{
                    val builder = AlertDialog.Builder(activity!!)
                    val dialogView = layoutInflater.inflate(R.layout.dialog_insertreview, null)
                    val dialogTitle = dialogView.findViewById<EditText>(R.id.text_title)
                    val dialogContent = dialogView.findViewById<EditText>(R.id.text_content)
                    val dialogRatingBar = dialogView.findViewById<RatingBar>(R.id.dialogRb)

                    builder.setView(dialogView)
                        .setPositiveButton("확인") { dialogInterface, i ->
                            VolleyService.insertReviewReq(UserInfo.NICKNAME, name, UserInfo.UNIV, dialogRatingBar.rating.toInt(), dialogContent.text.toString(), activity!!,{success ->

                            })
                        }
                        .setNegativeButton("취소") { dialogInterface, i ->
                            /* 취소일 때 아무 액션이 없으므로 빈칸 */
                        }
                        .show()
                }*/
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


                reviewRV.setHasFixedSize(true)
                reviewRV.layoutManager = LinearLayoutManager(activity)
                reviewRV.adapter = ReviewAdapter(reviewList)

            }
        })

        return inflater.inflate(R.layout.fragment_review, container, false)
    }
}
