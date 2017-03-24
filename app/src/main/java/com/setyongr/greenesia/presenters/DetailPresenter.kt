package com.setyongr.greenesia.presenters

import android.support.design.widget.Snackbar
import com.setyongr.greenesia.base.BasePresenter
import com.setyongr.greenesia.common.loadImg
import com.setyongr.greenesia.data.DataManager
import com.setyongr.greenesia.views.interfaces.DetailMvpView
import com.setyongr.greenesia.views.interfaces.EventMvpView
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_list.*
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import android.app.ProgressDialog
import android.util.Log
import com.setyongr.greenesia.data.models.EventData
import javax.inject.Inject


class DetailPresenter @Inject constructor(private var dataManager: DataManager): BasePresenter<DetailMvpView>() {
    var subscription: Subscription? = null
    var discover: EventData? = null

    override fun attachView(mvpView: DetailMvpView) {
        super.attachView(mvpView)
    }


    override fun detachView() {
        super.detachView()
        subscription?.unsubscribe()
    }


    fun loadMovie(id: String) {
        checkViewAttached()
        if (subscription != null && !(subscription as Subscription).isUnsubscribed()) {
            (subscription as Subscription).unsubscribe()
        }

        getMvpView()?.showProgress()
        subscription =  dataManager.getEventDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            receivedItem ->
                            Log.d("wooo", "wooo")
                            getMvpView()?.showDetail(receivedItem)
                            getMvpView()?.hideProgress()
                        },
                        {
                            e -> getMvpView()?.showError(e.message)
                        }
                )

    }

    fun takeEvent(id: String){
        checkViewAttached()
        if (subscription != null && !(subscription as Subscription).isUnsubscribed()) {
            (subscription as Subscription).unsubscribe();
        }

        getMvpView()?.showProgress()
        subscription = dataManager.takeEvent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            receivedItem ->
                            getMvpView()?.hideProgress()
                            getMvpView()?.onAfterTakeEvent(receivedItem.id)
                        },
                        {
                            e ->
                            e.printStackTrace()
                            getMvpView()?.hideProgress()
                            getMvpView()?.showError(e.message)

                        }
                )


    }
}
