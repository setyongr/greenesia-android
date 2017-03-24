package com.setyongr.greenesia.presenters

import com.setyongr.greenesia.base.BasePresenter
import com.setyongr.greenesia.data.DataManager
import com.setyongr.greenesia.data.models.TakeReward
import com.setyongr.greenesia.views.interfaces.TakeRewardMvpView
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TakeRewardPresenter @Inject constructor(private var dataManager: DataManager): BasePresenter<TakeRewardMvpView>() {
    var subscription: Subscription? = null
    var DataList: TakeReward? = null
    var skip: Int = -10

    override fun detachView() {
        super.detachView()
        subscription?.unsubscribe()
    }


    fun loadData() {
        checkViewAttached()
        if (subscription != null && !(subscription as Subscription).isUnsubscribed) {
            (subscription as Subscription).unsubscribe();
        }

        if (DataList == null || (DataList as TakeReward).has_more){
            subscription = dataManager.getTakeReward(10, skip.plus(10))
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