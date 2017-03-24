package com.setyongr.greenesia.presenters

import android.support.design.widget.Snackbar
import android.util.Log
import com.setyongr.greenesia.base.BasePresenter
import com.setyongr.greenesia.data.DataManager
import com.setyongr.greenesia.data.models.Event
import com.setyongr.greenesia.views.interfaces.EventMvpView
import kotlinx.android.synthetic.main.fragment_list.*
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EventPresenter @Inject constructor(private val dataManager: DataManager): BasePresenter<EventMvpView>() {
    var subscription: Subscription? = null
    var EventList: Event? = null
    var skip: Int = -10

    override fun attachView(mvpView: EventMvpView) {
        super.attachView(mvpView)
    }


    override fun detachView() {
        super.detachView()
        subscription?.unsubscribe()
    }


    fun loadDiscover() {
        checkViewAttached()
        if (subscription != null && !(subscription as Subscription).isUnsubscribed()) {
            (subscription as Subscription).unsubscribe();
        }

        val currentDateTimeString = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        if (EventList == null || (EventList as Event).has_more){
            subscription = dataManager.getEvent(10, skip.plus(10), null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                receivedItem ->
                                EventList = receivedItem
                                getMvpView()?.showList(receivedItem)

                            },
                            {
                                e -> getMvpView()?.showError(e.message)

                            }
                    )

        }
    }

}
