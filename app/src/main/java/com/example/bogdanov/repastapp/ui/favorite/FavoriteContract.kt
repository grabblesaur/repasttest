package com.example.bogdanov.repastapp.ui.favorite

import com.example.bogdanov.repastapp.base.BaseView
import com.example.bogdanov.repastapp.data.model.Restaraunt
import com.example.bogdanov.repastapp.data.model.Waiter
import java.util.*

interface FavoriteContract {
    interface Presenter {
        fun loadFavoriteDataList()
        fun attachView(view: FavoriteView)
        fun detachView()
    }
    interface FavoriteView : BaseView {
        // Список ресторанов, в которых пользователь
        // оставлял лайки официантам.
        // Сортировка происходит по количеству оставленных лайков
        // официантам по заведениям.

        // Дано: список ресторанов, список лайков, список официантов
        fun showData(hashMap: LinkedHashMap<Restaraunt, ArrayList<Waiter>>)
    }
}