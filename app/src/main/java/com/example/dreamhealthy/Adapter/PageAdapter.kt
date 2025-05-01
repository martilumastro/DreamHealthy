package com.example.dreamhealthy.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.dreamhealthy.Fragments.HomeFragment
import com.example.dreamhealthy.Fragments.TodayFragment
import com.example.dreamhealthy.Fragments.AddFragment

internal class PageAdapter (fm:FragmentManager?):
        FragmentPagerAdapter(fm!!){

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 ->{
                HomeFragment()
            }
            1 ->{
                TodayFragment()
            }
            2 ->{
                AddFragment()
            }
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {
        return 5
    }

}