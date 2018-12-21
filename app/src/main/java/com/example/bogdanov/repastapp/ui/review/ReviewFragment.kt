package com.example.bogdanov.repastapp.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bogdanov.repastapp.R
import com.example.bogdanov.repastapp.data.model.Restaraunt
import com.example.bogdanov.repastapp.di.Application
import kotlinx.android.synthetic.main.fragment_review.*
import javax.inject.Inject



private val TAG: String = ReviewFragment::class.java.simpleName

class ReviewFragment : Fragment(), ReviewContract.ReviewView {

    @Inject
    lateinit var presenter: ReviewPresenter

    private lateinit var adapter: RestarauntAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Application.appComponent.inject(this)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    private fun initViews() {

        reviewRecyclerView.layoutManager = (object : LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }

            override fun canScrollVertically(): Boolean {
                return false
            }
        })
        adapter = RestarauntAdapter(ArrayList())
        reviewRecyclerView.adapter = adapter

        presenter.getRestaraunt()
    }

    override fun showData(list: ArrayList<Restaraunt>) {
        if (::adapter.isInitialized) {
            adapter.replace(list)
        }
    }

    override fun showError(errorMessage: String) {

    }
}