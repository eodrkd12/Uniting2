package com.example.commit.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.ListItem.Menu
import com.example.commit.R
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuAdapter(val menu:ArrayList<Menu>) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return menu.count()-1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {
        holder.itemView.text_menu.text = menu.get(position).name
        holder.itemView.text_price.text = "· " + menu.get(position).price
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems() {

        }
    }
}