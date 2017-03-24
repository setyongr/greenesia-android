package com.setyongr.greenesia.views.organizer

import android.app.ProgressDialog
import android.content.Intent
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
import com.setyongr.greenesia.common.loadImg
import com.setyongr.greenesia.data.GnService
import com.setyongr.greenesia.data.models.EventData
import com.setyongr.greenesia.data.models.TakeEvent
import com.setyongr.greenesia.presenters.DetailPresenter
import com.setyongr.greenesia.presenters.organizer.AttendantPresenter
import com.setyongr.greenesia.views.ScannerActivity
import com.setyongr.greenesia.views.TakeEventDetailActivity
import com.setyongr.greenesia.views.adapters.AttendantAdapter
import com.setyongr.greenesia.views.interfaces.DetailMvpView
import com.setyongr.greenesia.views.interfaces.organizer.AttendantMvpView
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_organized_event_detail.*
import javax.inject.Inject


class EventDetailFragment : Fragment(), DetailMvpView {


    @Inject lateinit var mDetailPresenter: DetailPresenter

    var  dialog: ProgressDialog? = null
    var id: String = "0"

    companion object{
        fun newInstance(id: String): EventDetailFragment {
            val fragment = EventDetailFragment()
            val bundle = Bundle()
            bundle.putString("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = container?.inflate(R.layout.fragment_organized_event_detail)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApp.greenesiaComponent.inject(this)
        id = arguments.getString("id")
        mDetailPresenter.attachView(this)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        valTicket.setOnClickListener {
            context.startActivity(Intent(context, ScannerActivity::class.java))
        }
        if(savedInstanceState == null){
            mDetailPresenter.loadMovie(id!!)
        }
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

    }

    override fun onDestroy() {
        super.onDestroy()
        mDetailPresenter.detachView()
    }

    override fun showDetail(data: EventData) {
        imgPoster.loadImg(GnService.ENDPOINT + "static/" + data.gambar)
        textNama.text = "Nama :" + data.nama
        textDesc.text = "Deskripsi : " + data.deskripsi
        val sbDate = StringBuffer()
        sbDate.append(data.tanggal)
        val newDate = sbDate.substring(0, 19).toString()
        val rDate = newDate.replace("T", " ")
        val nDate = rDate.replace("-".toRegex(), "/")
        textTgl.text = "Tanggal: " + nDate

    }

    override fun showError(e: String?) {
        Snackbar.make(coordinator, e ?: "Error", Snackbar.LENGTH_LONG).show()
    }


    override fun showProgress() {
        dialog = ProgressDialog.show(context, "",
                "Loading. Please wait...", true)
    }

    override fun hideProgress() {
        dialog?.dismiss()
    }

    override fun onAfterTakeEvent(id: String) {

    }
}