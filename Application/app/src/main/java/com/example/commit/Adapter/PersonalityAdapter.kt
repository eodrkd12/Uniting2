package com.example.commit.Adapter
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.ListItem.Menu
import com.example.commit.ListItem.Personality
import com.example.commit.R
import kotlinx.android.synthetic.main.item_menu.view.*
import kotlinx.android.synthetic.main.item_personality.view.*

class PersonalityAdapter(val list:ArrayList<Personality>) : RecyclerView.Adapter<PersonalityAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalityAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_personality, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PersonalityAdapter.ViewHolder, position: Int) {
        holder.itemView.text_personalityhobby.text = list.get(position).title
        if(list.get(position).isSelected == true)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#87CEEB"))
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        holder.itemView.setOnClickListener {
            list.get(position).isSelected = !list.get(position).isSelected
            if(list.get(position).isSelected == true)
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#87CEEB"))
            }
            else
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems() {

        }
    }
}