package com.setyongr.greenesia.presenters.organizer

import com.setyongr.greenesia.base.BasePresenter
import com.setyongr.greenesia.data.DataManager
import com.setyongr.greenesia.data.models.Event
import com.setyongr.greenesia.data.models.TakeEvent
import com.setyongr.greenesia.views.interfaces.organizer.AttendantMvpView
import com.setyongr.greenesia.views.interfaces.organizer.EventListMvpView
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class AttendantPresenter @Inject constructor(private var dataManager: DataManager): BasePresenter<AttendantMvpView>() {
    var subscription: Subscription? = null
    var DataList: TakeEvent? = null
    var skip: Int = -10

    override fun detachView() {
        super.detachView()
        subscription?.unsubscribe()
    }


    fun loadData(id: String) {
        checkViewAttached()
        if (subscription != null && !(subscription as Subscription).isUnsubscribed) {
            (subscription as Subscription).unsubscribe()
        }

        if (DataList == null || (DataList as TakeEvent).has_more){
            subscription = dataManager.getAttendant(10, skip.plus(10), id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                receivedItem ->
                                DataList = receivedItem
                                getMvpView()?.showList(receivedItem)

                            },
                            {
                                e -> getMvpView()?.showError(e.message)

                            }
                    )

        }

    }
}