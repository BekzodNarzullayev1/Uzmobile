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
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.uzmobile.R
import com.example.uzmobile.adapters.PackageAdapter
import com.example.uzmobile.databinding.FragmentStatusBinding
import com.example.uzmobile.models.Package
import com.google.firebase.firestore.FirebaseFirestore

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class StatusFragment : Fragment() {
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

    lateinit var binding: FragmentStatusBinding
    lateinit var list: ArrayList<Package>
    lateinit var packageAdapter: PackageAdapter
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var acceptDialog: String
    lateinit var cancelDialog: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStatusBinding.inflate(layoutInflater)
        firebaseFirestore = FirebaseFirestore.getInstance()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val prefs = activity?.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = prefs?.getString("My_Lang", "")

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

        list = ArrayList()
        firebaseFirestore.collection("status")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    result?.forEach {
                        val toObject = it.toObject(Package::class.java)
                        if (toObject.lang == language) {
                            list.add(toObject)
                        }
                    }
                    packageAdapter = PackageAdapter(
                        list,
                        requireContext(),
                        object : PackageAdapter.OnItemClickListener {
                            override fun onItemClick(package1: Package) {
                                val alertDialog = AlertDialog.Builder(requireContext())
                                alertDialog.setIcon(R.drawable.logo_2)
                                    .setTitle("${package1.name}")
                                    .setMessage("${package1.info}")
                                    .setPositiveButton(
                                        "${acceptDialog}"
                                    ) { _, _ ->
                                        val code = package1.code?.substring(
                                            0,
                                            (package1.code?.length)?.minus(1)!!
                                        )
                                        val uri = "tel:${code}${Uri.encode("#")}"
                                        val intent = Intent(Intent.ACTION_CALL)
                                        intent.data = Uri.parse(uri)
                                        startActivity(intent)
                                    }
                                    .setNegativeButton("${cancelDialog}",
                                        object : DialogInterface.OnClickListener {
                                            override fun onClick(
                                                dialog: DialogInterface?,
                                                which: Int
                                            ) {

                                            }

                                        })
                                    .show()
                            }

                        })
                    binding.rv.adapter = packageAdapter
                }
            }

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.checkBtn.setOnClickListener {
            val uri = "tel:*100*6${Uri.encode("#")}"
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }

        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatusFragment().apply {
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