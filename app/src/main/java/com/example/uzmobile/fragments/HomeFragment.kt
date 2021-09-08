package com.example.uzmobile.fragments

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.uzmobile.R
import com.example.uzmobile.adapters.RvAdapter
import com.example.uzmobile.adapters.ViewPagerAdapter
import com.example.uzmobile.databinding.FragmentHomeBinding
import com.example.uzmobile.models.Status
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
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

    lateinit var binding: FragmentHomeBinding
    lateinit var handler: Handler
    private lateinit var statusList: ArrayList<Status>
    private lateinit var rvAdapter: RvAdapter
    lateinit var firebaseFirestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        firebaseFirestore = FirebaseFirestore.getInstance()

        val prefs = activity?.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = prefs?.getString("My_Lang","")

        statusList = ArrayList()
        firebaseFirestore.collection("statusTop")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    result?.forEach {
                        val status = it.toObject(Status::class.java)
                        if (status.lang == language){
                            statusList.add(status)
                        }
                    }
                    rvAdapter = RvAdapter(statusList, object : RvAdapter.OnItemClickListener {
                        override fun onItemClick(status: Status, position: Int) {
                            val bundle = Bundle()
                            bundle.putSerializable("status", status)
                            findNavController().navigate(R.id.bannerFragment, bundle)
                        }

                    })
                    binding.viewPager.adapter = rvAdapter
                    binding.indicator.setViewPager(binding.viewPager)
                }
            }

        handler = Handler()

        binding.apply {
            itemInternet.setOnClickListener {
                findNavController().navigate(R.id.internetPackageFragment)
            }
            itemSms.setOnClickListener {
                findNavController().navigate(R.id.messageFragment)
            }
            itemStatus.setOnClickListener {
                findNavController().navigate(R.id.statusFragment)
            }
            itemHash.setOnClickListener {
                findNavController().navigate(R.id.ussdFragment)
            }
            itemService.setOnClickListener {
                findNavController().navigate(R.id.serviceFragment)
            }
            itemPhone.setOnClickListener {
                findNavController().navigate(R.id.timeFragment)
            }
        }

        handler.postDelayed(runnable, 2000)
        return binding.root
    }

    val runnable: Runnable = object : Runnable {
        override fun run() {
            if (binding.viewPager.currentItem == statusList.size - 1) {
                binding.viewPager.currentItem = 0
            } else {
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            }
            handler.postDelayed(this, 2000)
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}