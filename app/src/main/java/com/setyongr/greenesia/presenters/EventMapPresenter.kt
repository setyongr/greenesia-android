package com.setyongr.greenesia.presenters

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import com.setyongr.greenesia.base.BasePresenter
import com.setyongr.greenesia.data.DataManager
import com.setyongr.greenesia.views.interfaces.EventMapMvpView
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class EventMapPresenter @Inject constructor(private var dataManager: DataManager): BasePresenter<EventMapMvpView>() {
    var subscription: Subscription? = null

    @Inject lateinit var pref: SharedPreferences
    @Inject lateinit var context: Context
    override fun detachView() {
        super.detachView()
        subscription?.unsubscribe()
    }

    fun getNearby(location: Location?){
        subscription = dataManager.getEvent(100, 0, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            receivedItem ->
                            getMvpView()?.drawMarker(receivedItem.data)
                        },
                        Throwable::printStackTrace
                )
    }
}