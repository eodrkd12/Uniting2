package com.example.commit.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.commit.ListItem.PostItem
import com.example.commit.R

class StudyAdapter : BaseAdapter() {
    private var studyList = ArrayList<PostItem>()

    override fun getCount(): Int {
        return studyList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return studyList.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val context: Context? = parent?.context

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_post_list, parent, false)
        }

        var textTitle=view?.findViewById(R.id.text_title) as TextView
        var textWriter=view.findViewById(R.id.text_writer) as TextView

        var item=studyList[position]

        textTitle.setText(item.title)
        textWriter.setText(item.writer)

        return view
    }

    fun addItem(title: String,writer: String){
        val item= PostItem()

        item.title=title
        item.writer=writer

        studyList.add(item)
    }

    fun clear(){
        studyList.clear()
    }
}