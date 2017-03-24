package com.setyongr.greenesia.views.organizer

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.setyongr.greenesia.R
import com.setyongr.greenesia.common.ViewPagerAdapter
import com.setyongr.greenesia.views.EventFragment
import com.setyongr.greenesia.views.EventmapFragment
import com.setyongr.greenesia.views.RewardFragment
import kotlinx.android.synthetic.main.activity_organized_event_detail.*

class OrganizedEventDetailActivity : AppCompatActivity() {

    var id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organized_event_detail)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        id = intent.getStringExtra("id")

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setupViewPager(viewpager)
        tabs.setupWithViewPager(viewpager)
        viewpager.setOffscreenPageLimit(3)
    }

    fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(EventDetailFragment.newInstance(id!!), "Detail")
        adapter.addFragment(AttendantFragment.newInstance(id!!), "Attendant")

        viewPager.adapter = adapter
    }
}
