package com.example.bogdanov.repastapp.data

import com.example.bogdanov.repastapp.data.model.FavoriteData
import com.example.bogdanov.repastapp.data.model.Restaraunt
import com.example.bogdanov.repastapp.data.model.Waiter
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("restoapp.php?param=restaraunts")
    fun getRestaraunts(): Single<ArrayList<Restaraunt>>

    @GET("restoapp.php")
    fun getWaiters(@Query("waiters") restarauntId: String): Single<ArrayList<Waiter>>

    @GET("restoapp.php?favorite=true&user_id=f241aac4c0592abc")
    fun getFavoriteDataList(): Single<ArrayList<FavoriteData>>
}