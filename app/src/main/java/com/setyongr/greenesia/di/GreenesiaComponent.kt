package com.setyongr.greenesia.di

import android.support.v4.app.Fragment
import com.setyongr.greenesia.views.*
import com.setyongr.greenesia.views.organizer.AttendantFragment
import com.setyongr.greenesia.views.organizer.EventDetailFragment
import com.setyongr.greenesia.views.organizer.EventListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class
))
interface GreenesiaComponent {
    fun inject(eventFragment: EventFragment)
    fun inject(eventmapFragment: EventmapFragment)
    fun inject(detailActivity: DetailActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(fragment: RewardFragment)
    fun inject(takeEventActivity: TakeEventActivity)
    fun inject(takeRewardActivity: TakeRewardActivity)
    fun inject(eventListActivity: EventListActivity)
    fun inject(attendantFragment: AttendantFragment)
    fun inject(eventDetailFragment: EventDetailFragment)
    fun inject(scannerActivity: ScannerActivity)
}