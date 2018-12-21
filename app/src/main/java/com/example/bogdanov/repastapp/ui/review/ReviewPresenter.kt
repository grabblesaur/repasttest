package com.example.bogdanov.repastapp.ui.review

import android.util.Log
import com.example.bogdanov.repastapp.data.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReviewPresenter @Inject constructor(private var apiService: ApiService) : ReviewContract.Presenter {

    var view: ReviewContract.ReviewView? = null

    override fun attachView(view: ReviewContract.ReviewView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun getRestaraunt() {
        val disposable = apiService.getRestaraunts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.showData(it)
                },
                {
                    it.message?.let { it1 -> view?.showError(it1) }
                })
    }
}