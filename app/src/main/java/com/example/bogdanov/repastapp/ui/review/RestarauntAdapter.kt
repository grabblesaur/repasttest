package com.example.bogdanov.repastapp.ui.review

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bogdanov.repastapp.GlideApp
import com.example.bogdanov.repastapp.R
import com.example.bogdanov.repastapp.data.model.Restaraunt
import kotlinx.android.synthetic.main.item_list.view.*

class RestarauntAdapter(list: ArrayList<Restaraunt>) : RecyclerView.Adapter<RestarauntAdapter.RestarauntViewHolder>() {

    private var restarauntList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestarauntViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return RestarauntViewHolder(view)
    }

    override fun getItemCount(): Int = restarauntList.size

    override fun onBindViewHolder(holder: RestarauntViewHolder, position: Int) {
        holder.onBind(restarauntList[position])
    }

    fun replace(list: ArrayList<Restaraunt>) {
        restarauntList = list
        notifyDataSetChanged()
    }

    inner class RestarauntViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(restaraunt: Restaraunt) {

            GlideApp.with(itemView)
                .load("http://cookit.zenithapps.ru/${restaraunt.image}")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(itemView.imageView)

            itemView.itemRestaraunt.setOnClickListener {
                Toast.makeText(itemView.context, "parent", Toast.LENGTH_SHORT).show()
            }

            itemView.titleTextView.text = restaraunt.name
            itemView.addressTextView.text = restaraunt.address
            itemView.ratingBar.rating = restaraunt.rate
            itemView.ratingTextView.text = String.format("%s (%s)", restaraunt.rate, restaraunt.people_rated)
            itemView.descTextView.text = restaraunt.description

            itemView.tipButton.setOnClickListener {
                Toast.makeText(itemView.context, "tip", Toast.LENGTH_SHORT).show()
            }

            itemView.phoneButton.setOnClickListener{
                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", restaraunt.phone, null))
                itemView.context.startActivity(intent)
            }
            itemView.gradeButton.setOnClickListener{
                Toast.makeText(itemView.context, "grade", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
