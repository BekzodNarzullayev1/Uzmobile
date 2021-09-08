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
import com.example.uzmobile.R
import com.example.uzmobile.adapters.JustAdapter
import com.example.uzmobile.databinding.FragmentCategoryBinding
import com.example.uzmobile.models.Category
import com.example.uzmobile.models.Package
import com.google.firebase.firestore.FirebaseFirestore


private const val ARG_PARAM1 = "category"

class CategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var category: Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getSerializable(ARG_PARAM1) as Category
        }
    }

    lateinit var binding: FragmentCategoryBinding
    lateinit var list: ArrayList<Package>
    lateinit var listCategory: ArrayList<Package>
    lateinit var justAdapter: JustAdapter
    lateinit var firebaseFirestore: FirebaseFirestore
    private val TAG = "CategoryFragment"
    lateinit var acceptDialog:String
    lateinit var cancelDialog:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        firebaseFirestore = FirebaseFirestore.getInstance()
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

        list = ArrayList()
        firebaseFirestore.collection("package")
            .get()
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    val result = task.result
                    result?.forEach { queryDocumentSnapshot ->
                        val packageObject = queryDocumentSnapshot.toObject(Package::class.java)
                        if (packageObject?.lang==language){
                            if (packageObject.lang==language){
                                list.add(packageObject)
                            }
                        }
                    }

                    Log.d(TAG, "onCreateView: ${list.size}")

                    listCategory = ArrayList()
                    for (i in list.indices){
                        if (list[i].categoryName==category?.name){
                            listCategory.add(list[i])
                        }
                    }

                    justAdapter = JustAdapter(listCategory, requireContext(),object :JustAdapter.OnItemClickListener{
                        override fun onItemClick(package2: Package) {
                            val alertDialog = AlertDialog.Builder(requireContext())
                            alertDialog.setIcon(R.drawable.logo_2)
                                .setTitle("${package2.name}")
                                .setMessage("${package2.info}")
                                .setPositiveButton("${acceptDialog}"
                                ) { _, _ ->
                                    val code = package2.code?.substring(
                                        0,
                                        (package2.code?.length)?.minus(1)!!
                                    )
                                    val uri = "tel:${code}${Uri.encode("#")}"
                                    Log.d(TAG, "onItemClick: $uri")
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

                    })
                    binding.rv.adapter = justAdapter
                }
            }


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(category: Category) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, category)
                }
            }
    }
}