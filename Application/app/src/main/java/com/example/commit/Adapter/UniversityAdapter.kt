package com.example.commit.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.commit.ListItem.UniversityItem
import com.example.commit.R
import java.util.*
import kotlin.collections.ArrayList

class UniversityAdapter : BaseAdapter(){

    private var universityList = ArrayList<UniversityItem>()

    override fun getCount(): Int {
        return universityList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return universityList.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val context:Context? = parent?.context
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_university, parent, false)
        }
        var textView=view?.findViewById(R.id.text_university) as TextView
        var item=universityList[position]
        textView.setText(item.university)
        return view
    }

    fun addItem(text: String,enable: Boolean){
        val item=UniversityItem()

        item.university=text
        item.enable=enable

        universityList.add(item)
    }

    fun clear(){
        universityList.clear()
    }

    /*fun filter(charText:String) {
        var charText = charText.toLowerCase(Locale.getDefault())
        universityList.clear()
        if(charText.length == 0)
        {

        }
    }*/
}