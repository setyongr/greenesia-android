package com.setyongr.greenesia.views.organizer

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.setyongr.greenesia.MainApp

import com.setyongr.greenesia.R
import com.setyongr.greenesia.common.InfiniteScrollListener
import com.setyongr.greenesia.data.models.Event
import com.setyongr.greenesia.presenters.organizer.EventListPresenter
import com.setyongr.greenesia.views.adapters.OrganizedEventAdapter
import com.setyongr.greenesia.views.interfaces.organizer.EventListMvpView
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class EventListActivity : AppCompatActivity(), EventListMvpView {

    @Inject lateinit var mEventListPresenter: EventListPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organizer_event_list)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        MainApp.greenesiaComponent.inject(this)
        mEventListPresenter.attachView(this)

        data_list.setHasFixedSize(true)
        val linearLayout = LinearLayoutManager(this)
        data_list.layoutManager = linearLayout
        data_list.clearOnScrollListeners()
        data_list.addOnScrollListener(InfiniteScrollListener({ mEventListPresenter.loadData()}, linearLayout))
        initAdapter()
        if(savedInstanceState == null){
            mEventListPresenter.loadData()
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initAdapter() {
        if (data_list.adapter == null) {
            data_list.adapter = OrganizedEventAdapter()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mEventListPresenter.detachView()
    }
    override fun showList(data: Event) {
        (data_list.adapter as OrganizedEventAdapter).addItem(data)
    }

    override fun showError(e: String?) {
        Snackbar.make(data_list, e ?: "", Snackbar.LENGTH_LONG).show()
    }


}
