package com.setyongr.greenesia.views

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.Menu
import android.view.MenuItem
import com.setyongr.greenesia.R
import com.setyongr.greenesia.common.ViewPagerAdapter
import com.setyongr.greenesia.views.EventFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.location.Location
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import android.support.annotation.NonNull
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.widget.*
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import com.google.android.gms.location.places.ui.PlaceAutocomplete.getStatus
import com.google.android.gms.location.LocationServices
import com.setyongr.greenesia.data.models.LocationEvent
import org.greenrobot.eventbus.EventBus
import android.widget.AdapterView.OnItemClickListener
import com.setyongr.greenesia.data.models.RmUser
import com.setyongr.greenesia.presenters.TakeRewardPresenter
import com.setyongr.greenesia.views.organizer.EventListActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.nav_header_main2.view.*


class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, NavigationView.OnNavigationItemSelectedListener {



    private val PERMISSION_ACCESS_FINE_LOCATION = 306
    private val REQUESTING_LOCATION_UPDATES_KEY = "requestingLocationUpdate"
    private val LOCATION_KEY = "currentLocation"
    private val LAST_UPDATED_TIME_STRING_KEY = "lastUpdatedLocation"

    private var  mGoogleApiClient: GoogleApiClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mRequestingLocationUpdates: Boolean = false
    private var mCurrentLocation: Location? = null
    private var mLastUpdateTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        setupViewPager(viewpager)
        tabs.setupWithViewPager(viewpager)
        viewpager.setOffscreenPageLimit(3)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_ACCESS_FINE_LOCATION)
        }

        if (mGoogleApiClient == null) {
            mGoogleApiClient = GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build()
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        Realm.init(this)
        val realm = Realm.getDefaultInstance()
        var user = realm.where(RmUser::class.java).findFirst()
        val nama = navigationView.getHeaderView(0).findViewById(R.id.navUserNama) as TextView
        val point = navigationView.getHeaderView(0).findViewById(R.id.navUserPoint) as TextView
        nama.text = user.nama
        point.text = "Point: " + user.point


    }

    fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(EventFragment.newInstance(), "Event")
        adapter.addFragment(EventmapFragment.newInstance(), "Map")
        adapter.addFragment(RewardFragment.newInstance(), "Reward")
        viewPager.adapter = adapter
    }
    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun createLocationRequest(){
        mLocationRequest = LocationRequest()
        mLocationRequest?.interval = 10000
        mLocationRequest?.fastestInterval = 5000
        mLocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    fun startLocationUpdates(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
        }
    }

    fun stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this)
    }

    override fun onLocationChanged(loc: Location) {
        mCurrentLocation = loc
        EventBus.getDefault().post(LocationEvent(loc))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_ACCESS_FINE_LOCATION -> if (grantResults.size <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        mGoogleApiClient?.connect()
        super.onStart()

    }

    override fun onStop() {
        mGoogleApiClient?.disconnect()
        super.onStop()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnected(p0: Bundle?) {
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient)
        EventBus.getDefault().post(LocationEvent(mCurrentLocation!!))
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            createLocationRequest()
            var builder = LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest)
            var result: PendingResult<LocationSettingsResult> = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build())
            result.setResultCallback {
                locationSettingResult ->
                val status = locationSettingResult.status
                when(status.statusCode){
                    LocationSettingsStatusCodes.SUCCESS -> {
                        mRequestingLocationUpdates = true
                        startLocationUpdates()
                    }

                }
            }
        }
    }
    override fun onConnectionSuspended(p0: Int) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPause() {
        super.onPause()
        if(mGoogleApiClient != null){
            if(mGoogleApiClient!!.isConnected && mRequestingLocationUpdates){
                stopLocationUpdates()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(mGoogleApiClient != null) {
            if (mGoogleApiClient!!.isConnected && !mRequestingLocationUpdates) {
                startLocationUpdates()
            }else{
                mGoogleApiClient?.connect()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.my_event) {
            startActivity(Intent(this, TakeEventActivity::class.java))

        } else if (id == R.id.my_reward) {
            startActivity(Intent(this, TakeRewardActivity::class.java))


        } else if (id == R.id.organizer) {
            startActivity(Intent(this, EventListActivity::class.java))

        }
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    

}
