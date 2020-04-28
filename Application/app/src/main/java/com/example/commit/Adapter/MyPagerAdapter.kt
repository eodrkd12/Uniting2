package com.example.commit.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
//import androidx.fragment.app.FragmentManager
import com.example.commit.Fragment.AlamFragment
import com.example.commit.Fragment.ChatFragment
import com.example.commit.Fragment.HomeFragment
import com.example.commit.Fragment.OptionFragment

class MyPagerAdapter(fm : androidx.fragment.app.FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) { //switch()문과 동일하다.
            0 -> {
                HomeFragment()
            }
            1 -> {
                ChatFragment()
            }
            2 -> {
                AlamFragment()
            }
            else -> {
                return OptionFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 4 //4개니깐
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Home"
            1 -> "Chat"
            2 -> "Alam"
            else -> {
                return "Option"
            }
        }
    }
}

