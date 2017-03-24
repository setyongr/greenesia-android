package com.setyongr.greenesia.views

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.setyongr.greenesia.MainApp
import com.setyongr.greenesia.R
import com.setyongr.greenesia.common.InfiniteScrollListener
import com.setyongr.greenesia.data.models.TakeEvent
import com.setyongr.greenesia.data.models.TakeReward
import com.setyongr.greenesia.presenters.TakeEventPresenter
import com.setyongr.greenesia.presenters.TakeRewardPresenter
import com.setyongr.greenesia.views.adapters.TakeEventAdapter
import com.setyongr.greenesia.views.adapters.TakeRewardAdapter
import com.setyongr.greenesia.views.interfaces.TakeEventMvpView
import com.setyongr.greenesia.views.interfaces.TakeRewardMvpView
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject


class TakeRewardActivity : AppCompatActivity(), TakeRewardMvpView {
    @Inject lateinit var mTakeRewardPresenter: TakeRewardPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_event)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        MainApp.greenesiaComponent.inject(this)
        mTakeRewardPresenter.attachView(this)

        data_list.setHasFixedSize(true)
        val linearLayout = LinearLayoutManager(this)
        data_list.layoutManager = linearLayout
        data_list.clearOnScrollListeners()
        data_list.addOnScrollListener(InfiniteScrollListener({ mTakeRewardPresenter.loadData()}, linearLayout))
        initAdapter()
        if(savedInstanceState == null){
            mTakeRewardPresenter.loadData()
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initAdapter() {
        if (data_list.adapter == null) {
            data_list.adapter = TakeRewardAdapter()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTakeRewardPresenter.detachView()
    }
    override fun showList(data: TakeReward) {
        (data_list.adapter as TakeRewardAdapter).addItem(data)
    }

    override fun showError(e: String?) {
        Snackbar.make(data_list, e ?: "", Snackbar.LENGTH_LONG).show()
    }

}
