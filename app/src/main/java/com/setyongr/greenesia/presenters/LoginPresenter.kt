package com.setyongr.greenesia.presenters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.setyongr.greenesia.base.BasePresenter
import com.setyongr.greenesia.common.AppPreferences
import com.setyongr.greenesia.data.DataManager
import com.setyongr.greenesia.data.models.RmUser
import com.setyongr.greenesia.views.interfaces.LoginMvpView
import com.setyongr.greenesia.views.MainActivity
import io.realm.Realm
import retrofit2.adapter.rxjava.HttpException
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class LoginPresenter @Inject constructor(private val dataManager: DataManager): BasePresenter<LoginMvpView>() {
    var subscription: Subscription? = null

    @Inject lateinit var pref: SharedPreferences
    @Inject lateinit var context: Context
    override fun detachView() {
        super.detachView()
        subscription?.unsubscribe()
    }

    fun getUser(){
        subscription = dataManager.myProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            receivedItem ->
                            Realm.init(context)
                            val realm = Realm.getDefaultInstance()
                            val result = realm.where(RmUser::class.java).findAll()

                            realm.beginTransaction()
                            result.deleteAllFromRealm()
                            val user: RmUser = realm.createObject(RmUser::class.java)
                            user.nama = receivedItem.nama
                            user.alamat = receivedItem.alamat
                            user.email = receivedItem.email
                            user.id = receivedItem.id
                            user.tipe = receivedItem.tipe
                            user.point = receivedItem.point
                            realm.commitTransaction()

                            getMvpView()?.onLoginSuccess()

                        },
                        {
                            it.printStackTrace()
                            getMvpView()?.hideProgress()
                        }
                )
    }
    fun doLogin(email: String, password: String){
        getMvpView()?.showProgress()
        subscription = dataManager.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            receivedItem ->
                            pref.edit().putBoolean(AppPreferences.IS_LOGGEDIN, true).apply()
                            pref.edit().putString(AppPreferences.AUTH_TOKEN, receivedItem.access_token).apply()
                            pref.edit().putString(AppPreferences.REFRESH_TOKEN, receivedItem.refresh_token).apply()
                            getUser()
                        },
                        {
                            it.printStackTrace()
                            if(it is HttpException){
                                if(it.code() == 500){
                                    Log.e("aaa", "aaaaa");
                                }
                            }
                            getMvpView()?.showError()
                            getMvpView()?.hideProgress()
                        }
                )
    }


}