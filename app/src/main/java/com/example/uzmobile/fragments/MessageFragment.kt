package com.example.uzmobile.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.uzmobile.R
import com.example.uzmobile.adapters.CategoryInternetAdapter
import com.example.uzmobile.adapters.CategorySmsAdapter
import com.example.uzmobile.databinding.FragmentMessageBinding
import com.example.uzmobile.models.Category
import com.example.uzmobile.models.Package
import com.google.android.material.tabs.TabLayout

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MessageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentMessageBinding
    lateinit var categoryList:ArrayList<Category>
    lateinit var categorySmsAdapter: CategorySmsAdapter
    lateinit var list: ArrayList<Package>
    lateinit var category1:String
    lateinit var category2:String
    lateinit var category3:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMessageBinding.inflate(layoutInflater)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        loadData()
        categorySmsAdapter = CategorySmsAdapter(childFragmentManager,categoryList)
        binding.viewPager.adapter = categorySmsAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        setTabs()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabView = tab?.customView
                val textView  = tabView?.findViewById<TextView>(R.id.txt)
                val ly  =tabView?.findViewById<LinearLayout>(R.id.ly)
                ly?.setBackgroundResource(R.drawable.item_tab2)
                textView?.setTextColor(Color.parseColor("#01B4FF"))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabView = tab?.customView
                val textView  = tabView?.findViewById<TextView>(R.id.txt)
                val ly  =tabView?.findViewById<LinearLayout>(R.id.ly)
                ly?.setBackgroundResource(R.drawable.item_tab)
                textView?.setTextColor(Color.WHITE)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        return binding.root
    }

    private fun loadData() {

        val prefs = activity?.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = prefs?.getString("My_Lang","")

        when (language) {
            "uz" -> {
                category1 = "Kunlik to'plamlar"
                category2 = "Oylik to'plamlar"
                category3 = "Xalqaro to'plamlar"
            }
            "ru" -> {
                category1 = "Ежедневные пакеты"
                category2 = "Ежемесячные пакеты"
                category3 = "Международный пакеты"
            }
            else -> {
                category1 = "Кунлик тўпламлар"
                category2 = "Ойлик тўпламлар"
                category3 = "Халқаро тўпламлар"
            }
        }

        categoryList = ArrayList()
        list = ArrayList()

        categoryList.add(Category("${category1}",list))
        categoryList.add(Category("${category2}",list))
        categoryList.add(Category("${category3}",list))

        binding.checkBtn.setOnClickListener {
            val uri = "tel:*105${Uri.encode("#")}"
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }
    }

    private fun setTabs() {
        val tabCount = binding.tabLayout.tabCount
        for (i in 0 until tabCount){
            val tabView:View = layoutInflater.inflate(R.layout.item_tab,null,false)
            val textView = tabView.findViewById<TextView>(R.id.txt)
            val ly = tabView.findViewById<LinearLayout>(R.id.ly)
            textView.text = categoryList[i].name
            if (i==0){
                ly?.setBackgroundResource(R.drawable.item_tab2)
                textView.setTextColor(Color.parseColor("#01B4FF"))
            }
            binding.tabLayout.getTabAt(i)?.customView = tabView
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MessageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}