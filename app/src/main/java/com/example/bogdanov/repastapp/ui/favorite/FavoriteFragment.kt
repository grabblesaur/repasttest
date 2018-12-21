package com.example.bogdanov.repastapp.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.bogdanov.repastapp.R
import com.example.bogdanov.repastapp.data.model.FavoriteRestaraunt
import com.example.bogdanov.repastapp.data.model.Restaraunt
import com.example.bogdanov.repastapp.data.model.Waiter
import com.example.bogdanov.repastapp.di.Application
import kotlinx.android.synthetic.main.fragment_favorite.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList



private val TAG: String = FavoriteFragment::class.java.simpleName

class FavoriteFragment : Fragment(), FavoriteContract.FavoriteView {

    @Inject
    lateinit var presenter: FavoritePresenter

    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Application.appComponent.inject(this)
        initViews()
    }

    private fun initViews() {
        // Removes blinks
//        (favRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        favRecyclerView.layoutManager = (object : LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }

            override fun canScrollVertically(): Boolean {
                return false
            }
        })
        adapter = FavoriteAdapter(ArrayList(), favParentLayout)
        favRecyclerView.adapter = adapter

        presenter.loadFavoriteDataList()
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    override fun showData(hashMap: LinkedHashMap<Restaraunt, ArrayList<Waiter>>) {
        Log.i(TAG, "restarauntList = ${hashMap.size}")
        if (::adapter.isInitialized) {
            val list = ArrayList<FavoriteRestaraunt>()
            hashMap.forEach {
                val favoriteRestaraunt = FavoriteRestaraunt(it.key, it.value, false)
                list.add(favoriteRestaraunt)
            }
            list.sortByDescending { it.waiterList.size }
            adapter.replace(list)
        }
    }

    override fun showError(errorMessage: String) {

    }
}

