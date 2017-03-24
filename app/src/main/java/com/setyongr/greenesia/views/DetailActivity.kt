package com.setyongr.greenesia.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.setyongr.greenesia.R
import com.setyongr.greenesia.common.loadImg
import com.setyongr.greenesia.data.DataManager
import com.setyongr.greenesia.presenters.DetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import android.app.ProgressDialog
import android.content.Intent
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.setyongr.greenesia.MainApp
import com.setyongr.greenesia.data.GnService
import com.setyongr.greenesia.data.models.EventData
import com.setyongr.greenesia.views.interfaces.DetailMvpView
import javax.inject.Inject


class DetailActivity : AppCompatActivity(), DetailMvpView, OnMapReadyCallback {
    var dialog: ProgressDialog? = null
    var mMap: GoogleMap? = null
    var pos: LatLng? = null
    var nama: String? = null
    var id: String? = null
    @Inject lateinit var mDetailPresenter:DetailPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        MainApp.greenesiaComponent.inject(this)

        id = intent.getStringExtra("id")
        mDetailPresenter.attachView(this)
        mDetailPresenter.loadMovie(id!!)

        fabBook.setOnClickListener {
            mDetailPresenter.takeEvent(id!!)
        }

        val mapView = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap!!.isMyLocationEnabled = true
        if(pos != null){
            mMap!!.addMarker(MarkerOptions().position(pos!!).title(nama))
            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 20f))
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        mDetailPresenter.detachView()
    }



    override fun showDetail(data: EventData) {
        collapsingToolbar.title = data.nama
        deskripsi.text = data.deskripsi
        val sbDate = StringBuffer()
        sbDate.append(data.tanggal)
        val newDate = sbDate.substring(0, 19).toString()
        val rDate = newDate.replace("T", " ")
        val nDate = rDate.replace("-".toRegex(), "/")
        tanggal.text = nDate
        alamat.text = data.alamat
        pos = LatLng(data.lokasi.coordinates[0], data.lokasi.coordinates[1])
        nama = data.nama
        backdrop.loadImg(GnService.ENDPOINT + "static/" + data.gambar)

        if(mMap != null){
            mMap!!.addMarker(MarkerOptions().position(pos!!).title(data.nama))
            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 20f))
        }
    }

    override fun showError(e: String?) {
        Snackbar.make(coordinator, e ?: "Error", Snackbar.LENGTH_LONG).show()
    }

    override fun showProgress() {
        dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true)
    }

    override fun hideProgress() {
        dialog?.dismiss()
    }

    override fun onAfterTakeEvent(id: String) {
        val intent = Intent(this, TakeEventDetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
