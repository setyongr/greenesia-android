package com.setyongr.greenesia.base

interface Presenter<in V: MvpView>{
    fun attachView(mvpView: V)
    fun detachView()
}