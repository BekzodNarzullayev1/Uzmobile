package com.example.uzmobile.adapters

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobile.R
import com.example.uzmobile.databinding.ItemPackage2Binding
import com.example.uzmobile.models.Package
import net.cachapa.expandablelayout.ExpandableLayout.OnExpansionUpdateListener


class JustAdapter(var list: List<Package>,var context: Context,var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<JustAdapter.Vh>() {
    inner class Vh(var itemPackage2Binding: ItemPackage2Binding) :
        RecyclerView.ViewHolder(itemPackage2Binding.root) {

        fun onBind(package2:Package) {
            itemPackage2Binding.expandableLayout0.setOnExpansionUpdateListener(OnExpansionUpdateListener { expansionFraction, state ->
                Log.d(
                    "ExpandableLayout0",
                    "State: $state"
                )
            })

            val prefs = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
            val language = prefs?.getString("My_Lang", "")
            when (language) {
                "uz" -> {
                    itemPackage2Binding.btnTxt.text = "Batafsil"
                }
                "ru" -> {
                    itemPackage2Binding.btnTxt.text = "Подробнее"
                }
                else -> {
                    itemPackage2Binding.btnTxt.text = "Батафсил"
                }
            }

            itemPackage2Binding.count.text = package2.count
            itemPackage2Binding.packageName.text = package2.name
            itemPackage2Binding.packageInfo.text = package2.info

            itemPackage2Binding.btnAccept.setOnClickListener {
                onItemClickListener.onItemClick(package2)
            }

            itemPackage2Binding.header.setOnClickListener {
                if (itemPackage2Binding.expandableLayout0.isExpanded()) {
                    itemPackage2Binding.expandableLayout0.collapse()
                    itemPackage2Binding.arrow.setImageResource(R.drawable.ic_down_arrov)
                }else {
                    itemPackage2Binding.expandableLayout0.expand()
                    itemPackage2Binding.arrow.setImageResource(R.drawable.ic_up_arrov)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemPackage2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener{
        fun onItemClick(package2: Package)
    }
}