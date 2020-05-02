package com.example.commit.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.R
import kotlinx.android.synthetic.main.item_drawerlist.view.*

class DrawerAdapter(var userList:ArrayList<String>): RecyclerView.Adapter<DrawerAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return userList.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_drawerlist, parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: DrawerAdapter.ViewHolder, position: Int) {
        holder.itemView.textView28.text=userList.get(position)

    }
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){


    }
}
