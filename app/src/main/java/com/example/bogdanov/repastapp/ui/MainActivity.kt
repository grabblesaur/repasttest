package com.example.bogdanov.repastapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.bogdanov.repastapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

private val TAG: String = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity() {

    private lateinit var reviewController: NavController
    private lateinit var favoriteController: NavController
    private lateinit var profileController: NavController
    private lateinit var currentController: NavController

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var returnValue = false
        when (item.itemId) {
            R.id.navigation_menu_review -> {
                currentController = reviewController
                sectionReviewWrapper.visibility = View.VISIBLE
                sectionFavoriteWrapper.visibility = View.INVISIBLE
                sectionProfileWrapper.visibility = View.INVISIBLE
                returnValue = true
            }
            R.id.navigation_menu_favorite -> {
                currentController = favoriteController
                sectionFavoriteWrapper.visibility = View.VISIBLE
                sectionReviewWrapper.visibility = View.INVISIBLE
                sectionProfileWrapper.visibility = View.INVISIBLE
                returnValue = true
            }
            R.id.navigation_menu_profile -> {
                currentController = profileController
                sectionProfileWrapper.visibility = View.VISIBLE
                sectionReviewWrapper.visibility = View.INVISIBLE
                sectionFavoriteWrapper.visibility = View.INVISIBLE
                returnValue = true
            }
        }
        return@OnNavigationItemSelectedListener returnValue
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    override fun supportNavigateUpTo(upIntent: Intent) {
        currentController.navigateUp()
    }

    override fun onBackPressed() {
        if (!currentController.popBackStack()) {
            finish()
        }
    }

    private fun initViews() {
        reviewController = Navigation.findNavController(this, R.id.sectionReview)
        favoriteController = Navigation.findNavController(this, R.id.sectionFavorite)
        profileController = Navigation.findNavController(this, R.id.sectionProfile)

        currentController = reviewController

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        sectionReviewWrapper.visibility = View.VISIBLE
        sectionFavoriteWrapper.visibility = View.INVISIBLE
        sectionProfileWrapper.visibility = View.INVISIBLE
    }
}
