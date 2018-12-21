package com.example.bogdanov.repastapp.data.model

data class Restaraunt(
    val id: Int,
    val name: String,
    val description: String,
    val address: String,
    val image: String,
    val rate: Float,
    val people_rated: Int,
    val phone: String)