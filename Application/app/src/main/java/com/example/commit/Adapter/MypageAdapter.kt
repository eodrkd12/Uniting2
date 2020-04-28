package com.example.commit.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.commit.ListItem.DatingItem
import com.example.commit.ListItem.MypageItem
import com.example.commit.R

 class MypageAdapter:BaseAdapter(){

     private var mypageItemList = ArrayList<MypageItem>()
     
     override fun getCount(): Int {
         return mypageItemList.size
     }

     override fun getItemId(position: Int): Long {
         return position.toLong()
     }

     override fun getItem(position: Int): Any {
         return mypageItemList.get(position)
     }

     override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
         var view = convertView
         val context:Context? = parent?.context

         // "listview_item" Layout을 inflate하여 convertView 참조 획득.
         if (view == null) {
             val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
             view = inflater.inflate(R.layout.item_mypage, parent, false)
         }

         var item=mypageItemList[position]

         var textName=view?.findViewById(R.id.text_item) as TextView
         textName.text=item.name

         return view
     }

     fun addItem(name: String){

         val item=MypageItem()

         item.name=name

         mypageItemList.add(item)
     }

     fun getName(position: Int):String?{
         return mypageItemList.get(position).name
     }
}