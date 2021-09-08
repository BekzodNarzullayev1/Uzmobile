package com.example.uzmobile.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.uzmobile.fragments.CategoryFragment
import com.example.uzmobile.fragments.CategorySmsFragment
import com.example.uzmobile.models.Category

class CategorySmsAdapter(private val fragmentManager: FragmentManager, var list: List<Category>):
    FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Fragment {
        return CategorySmsFragment.newInstance(list[position])
    }
}