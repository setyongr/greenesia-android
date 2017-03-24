package com.setyongr.greenesia.views

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.setyongr.greenesia.MainApp
import com.setyongr.greenesia.R
import com.setyongr.greenesia.common.inflate
import com.setyongr.greenesia.data.models.EventData
import com.setyongr.greenesia.data.models.LocationEvent
import com.setyongr.greenesia.presenters.EventMapPresenter
import com.setyongr.greenesia.views.interfaces.EventMapMvpView
import kotlinx.android.synthetic.main.fragment_eventmap.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import javax.inject.Inject

class EventmapFragment: Fragment(), EventMapMvpView, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    @Inject lateinit var mEventMapPresenter: EventMapPresenter
    var mLocation: Location? = null

    var mMap: GoogleMap? = null

    var markerMap = HashMap<Marker, String>()

    companion object{
        fun newInstance(): EventmapFragment {
            val fragment = EventmapFragment()
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        Log.d("as", "asu22")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = container?.inflate(R.layout.fragment_eventmap)
        view?.findViewById(R.id.mapView)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApp.greenesiaComponent.inject(this)
        mEventMapPresenter.attachView(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("as", "asu")
        mMap = googleMap
        mMap!!.isMyLocationEnabled = true
        mEventMapPresenter.getNearby(null)
        mMap?.setOnInfoWindowClickListener(this)
    }

    override fun onInfoWindowClick(p0: Marker?) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("id", markerMap[p0])
        context.startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
        EventBus.getDefault().unregister(this)
    }
    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }


    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLocationEvent(event: LocationEvent){
        mLocation = event.location
        mEventMapPresenter.getNearby(mLocation!!)

    }


    override fun drawMarker(data: List<EventData>) {
        for (d in data){
            val pos = LatLng(d.lokasi.coordinates[0], d.lokasi.coordinates[1])
            val markerOpt = MarkerOptions().position(pos).title(d.nama)
            val marker = mMap?.addMarker(markerOpt)
            if (marker != null) {
                markerMap.put(marker, d.id)
            }
        }
    }
}