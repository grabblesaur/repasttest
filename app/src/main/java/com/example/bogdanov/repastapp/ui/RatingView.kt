package com.example.bogdanov.repastapp.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.bogdanov.repastapp.R

class RatingView: LinearLayout {

    constructor(context: Context) : super(context) {
//        initViews(context)
    }

    constructor(context: Context,
                attrs: AttributeSet
    ) : super(context, attrs) {
//        initViews(context)
    }

    constructor(context: Context,
                attrs: AttributeSet,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        initViews(context)
    }

//    fun initViews(context: Context){
//        LayoutInflater
//                .from(context)
//                .inflate(R.layout.view_rating, this)
//    }

    fun setRating(rating: String?, isBig: Boolean){
        val ll = LinearLayout(context);
        removeAllViews()
        ll.orientation = LinearLayout.HORIZONTAL
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        ll.layoutParams = lp
        addView(ll)
        ll.removeAllViews()
        Log.e("RATING", "rating " + rating)
        for (i in 1..5){
            Log.e("RATING", "i " + i)
            val starImage = ImageView(context)
            if (rating!!.toInt() >= i){
                starImage.setImageResource( R.drawable.ic_star)
            } else{
                starImage.setImageResource(R.drawable.ic_star_inactive)

            }
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.setMargins(8, 0, 0, 0)
            starImage.layoutParams = lp
            ll.addView(starImage)
        }
        invalidate()
    }
}