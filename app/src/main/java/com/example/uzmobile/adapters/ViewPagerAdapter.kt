package com.example.uzmobile.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uzmobile.fragments.*

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BannerFragment()
            1 -> BannerFragment()
            2 -> BannerFragment()
            3 -> BannerFragment()
            4 -> BannerFragment()
            else -> BannerFragment()
        }
    }
}