package com.example.bogdanov.repastapp.di

import com.example.bogdanov.repastapp.ui.favorite.FavoriteFragment
import com.example.bogdanov.repastapp.ui.profile.ProfileFragment
import com.example.bogdanov.repastapp.ui.review.ReviewFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(fragment: ReviewFragment)
    fun inject(fragment: FavoriteFragment)
    fun inject(fragment: ProfileFragment)
}