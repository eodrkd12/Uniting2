package com.example.commit.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.commit.Class.UserInfo
import com.example.commit.ListItem.Homefeed
import com.example.commit.ListItem.Item
import com.example.commit.MainActivity.InformActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.cafeteria_item.view.*
import org.jetbrains.anko.doAsync

class CafeteriaGridAdapter(val context: Context, val homefeed: Homefeed) : RecyclerView.Adapter<CafeteriaGridAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return homefeed.items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeteriaGridAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_gridcafeteria, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CafeteriaGridAdapter.ViewHolder, position: Int) {
        var temp = homefeed.items.get(position).name
        var tags : String? = ""
        var title : String? = ""


        for(i in 0..temp!!.length-1){
            if(temp[i]=='<'||temp[i]=='>'||temp[i]=='/'||temp[i]=='b')
                continue
            else
                title+=temp[i]
        }
        holder.itemView.text_cafeteria_title.text = title
        doAsync {
            VolleyService.getReviewsScoreReq(title!!, UserInfo.UNIV, context, { success ->
                var point:String? = null
                point = success
                holder.itemView.text_starpoint.text="★"
                if(point == "null")
                {
                    holder.itemView.text_point.text = "0"
                }
                else
                {
                    if(point!!.length<3)
                        holder.itemView.text_point.text=point
                    else
                        holder.itemView.text_point.text = point!!.substring(0,3)
                }
            })
        }



        //holder.bindItems(homefeed.items.get(position))

        Glide.with(holder.itemView)
            .load(homefeed.items.get(position).imageSrc)
            .override(300,400)
            .into(holder.itemView.image_cafeteria)

        holder.view.setOnClickListener{
            val intent = Intent(context, InformActivity::class.java)
            intent.putExtra("name",homefeed.items.get(position).name)
            intent.putExtra("x", homefeed.items.get(position).x)
            intent.putExtra("y", homefeed.items.get(position).y)
            intent.putExtra("phone", homefeed.items.get(position).phone)
            intent.putExtra("id", homefeed.items.get(position).id)
            intent.putExtra("roadAddr", homefeed.items.get(position).roadAddr)
            intent.putExtra("options", homefeed.items.get(position).options)
            intent.putExtra("bizHourInfo", homefeed.items.get(position).bizHourInfo)
            if(homefeed.items.get(position).tags == null)
            {
                intent.putExtra("tags", "")
            }
            else
            {
                for(i in 0..homefeed.items.get(position).tags!!.size-1)
                {
                    tags += "#" + homefeed.items.get(position).tags!!.get(i) + " "
                }
                intent.putExtra("tags", tags)
            }
            context.startActivity(intent)
        }


    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: Item) {
            //itemView.text_starpoint.text = data.starPoint

        }
    }
}