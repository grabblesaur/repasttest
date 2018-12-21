package com.example.bogdanov.repastapp.ui.favorite

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bogdanov.repastapp.GlideApp
import com.example.bogdanov.repastapp.R
import com.example.bogdanov.repastapp.data.model.FavoriteRestaraunt
import kotlinx.android.synthetic.main.item_favorite_restaurant.view.*

class FavoriteAdapter(
    list: ArrayList<FavoriteRestaraunt>,
    parentView: NestedScrollView
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var mList = list
    private var mParentView = parentView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_restaurant, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.onBind(mList[position], position)
    }

    fun replace(list: ArrayList<FavoriteRestaraunt>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(favoriteRestaraunt: FavoriteRestaraunt, position: Int) {
            val restaurant = favoriteRestaraunt.restaurant
            val waiterList = favoriteRestaraunt.waiterList

            GlideApp.with(itemView)
                .load("http://cookit.zenithapps.ru/${restaurant.image}")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(itemView.imageView)

            itemView.itemFavRestaurant.setOnClickListener {}
            itemView.titleTextView.text = restaurant.name
            itemView.addressTextView.text = restaurant.address
            itemView.descTextView.text = restaurant.description

            itemView.tipButton.setOnClickListener {  }

            itemView.phoneButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", restaurant.phone, null))
                itemView.context.startActivity(intent)
            }

            itemView.gradeButton.setOnClickListener {  }

            itemView.waitersCountTextView.text = waiterList.size.toString()

            if (!favoriteRestaraunt.isExpanded) {
                itemView.collapseImageView
                    .setImageResource(R.drawable.ic_expand_more)
                itemView.expandableLayout.collapse(false)
            } else {
                itemView.collapseImageView
                    .setImageResource(R.drawable.ic_expand_less)
                itemView.expandableLayout.expand(false)
            }

            itemView.collapseButton.setOnClickListener {
                val isExpanded = favoriteRestaraunt.isExpanded
                favoriteRestaraunt.isExpanded = !isExpanded
                notifyDataSetChanged()
            }

            itemView.waitersRecyclerView.layoutManager = (object : LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false) {
                override fun canScrollHorizontally(): Boolean {
                    return false
                }

                override fun canScrollVertically(): Boolean {
                    return false
                }
            })

            itemView.waitersRecyclerView.adapter = WaiterAdapter(waiterList)

        }
    }
}
