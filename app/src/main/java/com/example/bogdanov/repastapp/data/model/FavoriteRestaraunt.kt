package com.example.bogdanov.repastapp.data.model


data class FavoriteRestaraunt(
    val restaurant: Restaraunt,
    val waiterList: ArrayList<Waiter>,
    var isExpanded: Boolean = false
)