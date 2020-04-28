package com.example.commit.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.ListItem.Menu
import com.example.commit.R
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuPreviewAdapter(val menu:ArrayList<Menu>, val count: Int) : RecyclerView.Adapter<MenuPreviewAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return count
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuPreviewAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuPreviewAdapter.ViewHolder, position: Int) {
        holder.itemView.text_menu.text = menu.get(position).name
        holder.itemView.text_price.text = "· " + menu.get(position).price
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems() {

        }
    }
}