package com.example.commit.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.ListItem.ReviewItem
import com.example.commit.R
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewPreviewAdapter(val reviewList: ArrayList<ReviewItem>, val count: Int): RecyclerView.Adapter<ReviewPreviewAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return count
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewPreviewAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReviewPreviewAdapter.ViewHolder, position: Int) {
        holder.itemView.text_reviewnickname.text = reviewList.get(position).nickname
        holder.itemView.text_reviewpoint.text = reviewList.get(position).starpoint
        holder.itemView.text_reviewcontent.text = reviewList.get(position).content
        holder.itemView.text_reviewdate.text = reviewList.get(position).date!!.substring(0, 10)
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    }

}