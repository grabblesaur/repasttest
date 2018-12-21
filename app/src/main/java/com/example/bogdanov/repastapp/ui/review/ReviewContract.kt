package com.example.bogdanov.repastapp.ui.review

import com.example.bogdanov.repastapp.base.BaseView
import com.example.bogdanov.repastapp.data.model.Restaraunt

interface ReviewContract {
    interface Presenter {
        fun getRestaraunt()
        fun attachView(view: ReviewView)
        fun detachView()
    }
    interface ReviewView : BaseView {
        fun showData(list: ArrayList<Restaraunt>)
    }
}