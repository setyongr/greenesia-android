package com.setyongr.greenesia.views.organizer

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setyongr.greenesia.MainApp
import com.setyongr.greenesia.R
import com.setyongr.greenesia.common.InfiniteScrollListener
import com.setyongr.greenesia.common.inflate
import com.setyongr.greenesia.data.models.Event
import com.setyongr.greenesia.data.models.TakeEvent
import com.setyongr.greenesia.presenters.EventPresenter
import com.setyongr.greenesia.presenters.organizer.AttendantPresenter
import com.setyongr.greenesia.views.adapters.AttendantAdapter
import com.setyongr.greenesia.views.adapters.EventAdapter
import com.setyongr.greenesia.views.adapters.TakeEventAdapter
import com.setyongr.greenesia.views.interfaces.EventMvpView
import com.setyongr.greenesia.views.interfaces.organizer.AttendantMvpView
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject


class AttendantFragment : Fragment(), AttendantMvpView {

    @Inject lateinit var mAttendantPresenter: AttendantPresenter
    var id: String = "0"

    companion object{
        fun newInstance(id: String): AttendantFragment {
            val fragment = AttendantFragment()
            val bundle = Bundle()
            bundle.putString("id", id)
            fragment.arguments = bundle
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
        id = arguments.getString("id")
        mAttendantPresenter.attachView(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        data_list.setHasFixedSize(true)
        val linearLayout = LinearLayoutManager(context)
        data_list.layoutManager = linearLayout
        data_list.clearOnScrollListeners()
        data_list.addOnScrollListener(InfiniteScrollListener({ mAttendantPresenter.loadData(id)}, linearLayout))

        initAdapter()

        if (savedInstanceState == null) {
            mAttendantPresenter.loadData(id)
        }
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

    }
    override fun showList(data: TakeEvent) {
        (data_list.adapter as AttendantAdapter).addItem(data)
    }

    override fun showError(e: String?) {
        Snackbar.make(data_list, e ?: "", Snackbar.LENGTH_LONG).show()
    }


    private fun initAdapter() {
        if (data_list.adapter == null) {
            data_list.adapter = AttendantAdapter()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mAttendantPresenter.detachView()
    }
}
