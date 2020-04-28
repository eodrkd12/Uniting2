package com.example.commit.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.MenuAdapter
import com.example.commit.ListItem.Menu

import com.example.commit.R
import kotlinx.android.synthetic.main.fragment_menu.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jsoup.Jsoup


class MenuFragment(val menu:ArrayList<Menu>) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_menu, container, false)
        var menuRV: RecyclerView = rootView.findViewById(R.id.menuRV)

        menuRV.adapter = MenuAdapter(menu)
        menuRV.setHasFixedSize(true)
        menuRV.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)

        return rootView
    }
}
