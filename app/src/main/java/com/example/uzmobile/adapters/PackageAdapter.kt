package com.example.uzmobile.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobile.R
import com.example.uzmobile.databinding.ItemPackageBinding
import com.example.uzmobile.models.Package

class PackageAdapter(var list: List<Package>,var context: Context,var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<PackageAdapter.Vh>() {
    inner class Vh(var itemPackageBinding: ItemPackageBinding) :
        RecyclerView.ViewHolder(itemPackageBinding.root) {

        fun onBind(package1:Package) {
            itemPackageBinding.packageInfo.text = package1.info
            itemPackageBinding.packageName.text = package1.name

            val prefs = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
            val language = prefs?.getString("My_Lang", "")
            when (language) {
                "uz" -> {
                    itemPackageBinding.btnTxt.text = "Batafsil"
                }
                "ru" -> {
                    itemPackageBinding.btnTxt.text = "Подробнее"
                }
                else -> {
                    itemPackageBinding.btnTxt.text = "Батафсил"
                }
            }

            itemPackageBinding.btnAccept.setOnClickListener {
                onItemClickListener.onItemClick(package1)
            }

            itemPackageBinding.header.setOnClickListener {
                if (itemPackageBinding.expandableLayout0.isExpanded) {
                    itemPackageBinding.expandableLayout0.collapse()
                    itemPackageBinding.arrow.setImageResource(R.drawable.ic_down_arrov)
                }else {
                    itemPackageBinding.expandableLayout0.expand()
                    itemPackageBinding.arrow.setImageResource(R.drawable.ic_up_arrov)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemPackageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener{
        fun onItemClick(package1: Package)
    }
}