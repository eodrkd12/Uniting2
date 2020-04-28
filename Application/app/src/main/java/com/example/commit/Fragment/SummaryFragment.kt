package com.example.commit.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.commit.ListItem.Menu
import com.example.commit.MainActivity.InformActivity
import com.example.commit.R
import kotlinx.android.synthetic.main.fragment_summary.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jsoup.Jsoup

class SummaryFragment(val roadAddr:String, var bizHourInfo:String, var options:String, var tags:String) : Fragment(){
    companion object {
        var index:Int = 0
        var timeData:String? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView = inflater.inflate(R.layout.fragment_summary, container, false)

        var textroadAddr:TextView = rootView.findViewById(R.id.text_roadaddr)
        var textOpenClose:TextView = rootView.findViewById(R.id.text_openclosetime)
        var textOptions:TextView = rootView.findViewById(R.id.text_options)
        var textHashtag:TextView = rootView.findViewById(R.id.text_hashtag)
        var check :Boolean = true
        var timeOpenClose:String = ""


        if("|" in bizHourInfo)
        {
            index =  bizHourInfo.indexOf("|")
            timeOpenClose = bizHourInfo.substring(0, index-1)
            textOpenClose.text = timeOpenClose + " ∨"
            timeData = bizHourInfo.substring(index+2, bizHourInfo.length).replace(" | ", "\n")
            textOpenClose.setOnClickListener{
                if(check == true)
                {
                    textOpenClose.text = timeOpenClose + " ∧\n" + timeData
                    check = false
                }
                else
                {
                    textOpenClose.text = timeOpenClose + " ∨"
                    check = true
                }
            }
        }
        else
        {
            textOpenClose.text = bizHourInfo
        }


        textroadAddr.text = roadAddr
        textOptions.text = options
        textHashtag.text = tags


        return rootView
    }
}