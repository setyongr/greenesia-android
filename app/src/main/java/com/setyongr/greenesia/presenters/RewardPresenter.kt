package com.setyongr.greenesia.presenters

import com.setyongr.greenesia.base.BasePresenter
import com.setyongr.greenesia.data.DataManager
import com.setyongr.greenesia.data.models.Reward
import com.setyongr.greenesia.views.interfaces.RewardMvpView
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RewardPresenter @Inject constructor(private var dataManager: DataManager): BasePresenter<RewardMvpView>(){
    var subscription: Subscription? = null
    var DataList: Reward? = null
    var skip: Int = -10

    override fun detachView() {
        super.detachView()
        subscription?.unsubscribe()
    }


    fun loadReward() {
        checkViewAttached()
        if (subscription != null && !(subscription as Subscription).isUnsubscribed) {
            (subscription as Subscription).unsubscribe()
        }

        val currentDateTimeString = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        if (DataList == null || (DataList as Reward).has_more){
            subscription = dataManager.getReward(10, skip.plus(10))
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