package com.setyongr.greenesia.views

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setyongr.greenesia.MainApp
import com.setyongr.greenesia.R
import com.setyongr.greenesia.base.RxBaseFragment
import com.setyongr.greenesia.common.InfiniteScrollListener
import com.setyongr.greenesia.common.inflate
import com.setyongr.greenesia.data.models.Event
import com.setyongr.greenesia.data.models.EventData
import com.setyongr.greenesia.presenters.EventPresenter
import com.setyongr.greenesia.views.adapters.EventAdapter
import com.setyongr.greenesia.views.interfaces.EventMvpView
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class EventFragment : Fragment(), EventMvpView {

    @Inject lateinit var mEventPresenter: EventPresenter


    companion object{
        fun newInstance(): EventFragment {
            val fragment = EventFragment()
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = container?.inflate(R.layout.fragment_list)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApp.greenesiaComponent.inject(this)

        mEventPresenter.attachView(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        data_list.setHasFixedSize(true)
        val linearLayout = LinearLayoutManager(context)
        data_list.layoutManager = linearLayout
        data_list.clearOnScrollListeners()
        data_list.addOnScrollListener(InfiniteScrollListener({ mEventPresenter.loadDiscover()}, linearLayout))

        initAdapter()

        if (savedInstanceState == null) {
            mEventPresenter.loadDiscover()
        }
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

    }
    override fun showList(data: Event) {
        (data_list.adapter as EventAdapter).addItem(data)
    }

    override fun showError(e: String?) {
        Snackbar.make(data_list, e ?: "", Snackbar.LENGTH_LONG).show()
    }


    private fun initAdapter() {
        if (data_list.adapter == null) {
            data_list.adapter = EventAdapter()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mEventPresenter.detachView()
    }
}
