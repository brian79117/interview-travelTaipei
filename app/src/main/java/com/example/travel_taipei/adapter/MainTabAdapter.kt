package com.example.travel_taipei.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.travel_taipei.MainApplication.Companion.appResources
import com.example.travel_taipei.R
import com.example.travel_taipei.ui.fragment.AttractionsFragment
import com.example.travel_taipei.ui.fragment.NewsFragment

class MainTabAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = appResources.getStringArray(R.array.main_tabTitle).size

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> NewsFragment()
        1 -> AttractionsFragment()
        else -> NewsFragment()
    }
}