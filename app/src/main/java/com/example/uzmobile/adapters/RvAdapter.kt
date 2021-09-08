package com.example.uzmobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobile.databinding.ItemBannerBinding
import com.example.uzmobile.models.Status

class RvAdapter(var list: List<Status>,var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<RvAdapter.Vh>() {
    inner class Vh(var itemBannerBinding: ItemBannerBinding) :
        RecyclerView.ViewHolder(itemBannerBinding.root) {

        fun onBind(status: Status,position: Int) {
            itemBannerBinding.minTxt.text = status.min
            itemBannerBinding.netTxt.text = status.mb
            itemBannerBinding.smsTxt.text = status.sms
            itemBannerBinding.nameStatus.text = status.name
            itemBannerBinding.price.text = status.price
            itemBannerBinding.root.setOnClickListener {
                onItemClickListener.onItemClick(status,position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener{
        fun onItemClick(status: Status,position: Int)
    }
}