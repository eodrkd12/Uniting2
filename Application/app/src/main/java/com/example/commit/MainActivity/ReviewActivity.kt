package com.example.commit.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.MenuAdapter
import com.example.commit.Adapter.ReviewAdapter
import com.example.commit.ListItem.Menu
import com.example.commit.ListItem.ReviewItem
import com.example.commit.R
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_review.*

class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)


        var reviewList = intent.getSerializableExtra("reviewList") as ArrayList<ReviewItem>

        rv_viewreview.adapter = ReviewAdapter(reviewList)
        rv_viewreview.setHasFixedSize(true)
        rv_viewreview.layoutManager = LinearLayoutManager(this@ReviewActivity, RecyclerView.VERTICAL, false)
    }
}
