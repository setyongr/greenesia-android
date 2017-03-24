package com.setyongr.greenesia.views.interfaces

import com.setyongr.greenesia.base.MvpView

interface LoginMvpView: MvpView {
    fun showProgress()
    fun hideProgress()
    fun onLoginSuccess()
    fun showError()
}