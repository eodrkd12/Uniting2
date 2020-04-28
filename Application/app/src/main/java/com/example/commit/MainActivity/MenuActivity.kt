package com.example.commit.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.MenuAdapter
import com.example.commit.Adapter.MenuPreviewAdapter
import com.example.commit.ListItem.Menu
import com.example.commit.R
import kotlinx.android.synthetic.main.activity_inform.*
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        var intent = intent

        var menuList = intent.getSerializableExtra("menuList") as ArrayList<Menu>

        rv_viewmenu.adapter = MenuAdapter(menuList)
        rv_viewmenu.setHasFixedSize(true)
        rv_viewmenu.layoutManager = LinearLayoutManager(this@MenuActivity, RecyclerView.VERTICAL, false)
    }
}
