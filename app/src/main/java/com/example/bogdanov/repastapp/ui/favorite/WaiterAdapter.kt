package com.example.bogdanov.repastapp.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.bogdanov.repastapp.GlideApp
import com.example.bogdanov.repastapp.R
import com.example.bogdanov.repastapp.data.model.Waiter
import kotlinx.android.synthetic.main.item_waiter.view.*

class WaiterAdapter(waiterList: ArrayList<Waiter>) : RecyclerView.Adapter<WaiterAdapter.WaiterViewHolder>() {

    private var mWaiterList = waiterList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaiterViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_waiter, parent, false)
        return WaiterViewHolder(view)
    }

    override fun getItemCount() = mWaiterList.size

    override fun onBindViewHolder(holder: WaiterViewHolder, position: Int) {
        holder.onBind(mWaiterList[position])
    }


    inner class WaiterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(waiter: Waiter) {
            GlideApp.with(itemView)
                .load("http://cookit.zenithapps.ru/${waiter.photo}")
                .transform(MultiTransformation(CenterCrop(), RoundedCorners(100)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.imageView)

            itemView.nameTextView.text = waiter.name
        }
    }
}
