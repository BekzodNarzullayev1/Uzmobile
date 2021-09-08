package com.example.uzmobile.fragments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.uzmobile.MainActivity
import com.example.uzmobile.R
import com.example.uzmobile.databinding.FragmentSettingBinding
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SettingFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(layoutInflater)

        binding.btnUzb.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra("lang","ru")
            this.startActivity(intent)
            requireActivity().finish()
        }
        binding.btnRus.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra("lang","ru")
            this.startActivity(intent)
            requireActivity().finish()
        }
        binding.btnKrill.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra("lang","kk")
            this.startActivity(intent)
            requireActivity().finish()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}