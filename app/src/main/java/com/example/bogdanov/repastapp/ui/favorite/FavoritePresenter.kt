package com.example.bogdanov.repastapp.ui.favorite

import android.util.Log
import com.example.bogdanov.repastapp.data.ApiService
import com.example.bogdanov.repastapp.data.model.Restaraunt
import com.example.bogdanov.repastapp.data.model.Waiter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.collections.LinkedHashMap

private val TAG: String = FavoritePresenter::class.java.simpleName

class FavoritePresenter @Inject constructor(private var apiService: ApiService) : FavoriteContract.Presenter {

    var view: FavoriteContract.FavoriteView? = null

    override fun attachView(view: FavoriteContract.FavoriteView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun loadFavoriteDataList() {
        Log.i(TAG, "loadData():")
        val disposable = apiService.getFavoriteDataList()
            .map { fdList ->
                val testMap: LinkedHashMap<Int, HashSet<Int>> = LinkedHashMap()
                for (fd in fdList) {
                    if (testMap.contains(fd.rest_id)) {
                        testMap[fd.rest_id]!!.add(fd.waiter_id)
                    } else  {
                        val hs = HashSet<Int>()
                        hs.add(fd.waiter_id)
                        testMap[fd.rest_id] = hs
                    }
                }
                testMap
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.i(TAG, "loadData(): onSuccess ${it.size}")
                    loadRestaurants(it)
                },
                { it.message?.let { it1 -> view?.showError(it1) } })
    }

    private fun loadRestaurants(hashMap: LinkedHashMap<Int, HashSet<Int>>) {
        Log.i(TAG, "loadRealData():")
        val disposable = apiService.getRestaraunts()
            .map { restaurants ->
                restaurants.filter { hashMap.containsKey(it.id) }
            }
            .map { it ->
                val map = LinkedHashMap<Restaraunt, HashSet<Int>>()
                for (r in it) {
                    map[r] = HashSet()
                    map[r]!!.addAll(hashMap[r.id]!!)
                }
                map
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(TAG, "loadRestaurants(): onSuccess ${it.size}")
                loadWaiters(it)
            },{
                it.message?.let { it1 -> view?.showError(it1) }
            })
    }

    private fun loadWaiters(hashMap: LinkedHashMap<Restaraunt, HashSet<Int>>?) {
        Log.i(TAG, "loadWaiters")

        val resultHashMap = LinkedHashMap<Restaraunt, ArrayList<Waiter>>()


        val observable = Observable.fromIterable(hashMap!!.keys)
            .flatMapSingle { restaurant ->
                apiService.getWaiters(restaurant.id.toString())
                    .map { waiterList ->
                        waiterList.filter { hashMap[restaurant]!!.contains(it.id) }
                    }
                    .map { it as ArrayList }
                    .map { it ->
                        resultHashMap[restaurant] = it
                        it
                    }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                Log.i(TAG, "onComplete(): $resultHashMap")
                view?.showData(resultHashMap)
            }
            .subscribe({
                Log.i(TAG, "loadWaiters(): onSuccess $it")
            }, {
                it.message?.let { it1 -> view?.showError(it1) }
            })


    }
}
