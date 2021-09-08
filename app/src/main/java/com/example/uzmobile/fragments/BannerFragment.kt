package com.example.uzmobile.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.uzmobile.R
import com.example.uzmobile.databinding.FragmentBannerBinding
import com.example.uzmobile.models.Status

private const val ARG_PARAM1 = "status"

class BannerFragment : Fragment() {
    private var status: Status? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            status = it.getSerializable(ARG_PARAM1) as Status
        }
    }

    lateinit var binding: FragmentBannerBinding
    lateinit var acceptDialog:String
    lateinit var cancelDialog:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBannerBinding.inflate(layoutInflater)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val prefs = activity?.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = prefs?.getString("My_Lang","")

        when (language) {
            "uz" -> {
                acceptDialog = "Bajarish"
                cancelDialog = "Bekor qilish"
            }
            "ru" -> {
                acceptDialog = "Делать"
                cancelDialog = "Отмена"
            }
            else -> {
                acceptDialog = "Accept"
                cancelDialog = "Cancel"
            }
        }

        binding.nameStatus.text = status?.name
        binding.statusName2.text = status?.name
        binding.minTxt.text = status?.min
        binding.netTxt.text = status?.mb
        binding.smsTxt.text = status?.sms
        binding.price1.text = status?.price
        binding.price2.text = status?.price
        binding.info.text = status?.info

        binding.btnMore.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setIcon(R.drawable.logo_2)
                .setTitle("${status?.name}")
                .setMessage("${status?.info}")
                .setPositiveButton("$acceptDialog"
                ) { _, _ ->
                    val code = status?.code?.substring(
                        0,
                        (status?.code?.length)?.minus(1)!!
                    )
                    Toast.makeText(requireContext(), "$code", Toast.LENGTH_SHORT).show()
                    val uri = "tel:${code}${Uri.encode("#")}"
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse(uri)
                    startActivity(intent)
                }
                .setNegativeButton("$cancelDialog",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                    }

                })
                .show()
        }

        binding.btnAccept.setOnClickListener {
            val code = status?.code?.substring(
                0,
                (status?.code?.length)?.minus(1)!!
            )
            val uri = "tel:${code}${Uri.encode("#")}"
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.actionBarTxt.text = status?.name

        return binding.root
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