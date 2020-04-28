package com.example.commit.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.commit.ListItem.DepartmentItem
import com.example.commit.R

class DepartmentAdapter : BaseAdapter(){

    private var departmentList = ArrayList<DepartmentItem>()

    override fun getCount(): Int {
        return departmentList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return departmentList.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val context:Context? = parent?.context

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_department, parent, false)
        }

        var textView=view?.findViewById(R.id.text_department) as TextView

        var item=departmentList[position]

        textView.setText(item.department)

        return view
    }

    fun addItem(text: String,enable: Boolean){
        val item=DepartmentItem()

        item.department=text
        item.enable=enable

        departmentList.add(item)
    }

    fun clear(){
        departmentList.clear()
    }
}