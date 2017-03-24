package com.setyongr.greenesia.views.interfaces

import com.setyongr.greenesia.base.MvpView
import com.setyongr.greenesia.data.models.EventData

interface DetailMvpView: MvpView {
    fun showDetail(data: EventData)
    fun showError(e: String?)
    fun showProgress()
    fun hideProgress()
    fun onAfterTakeEvent(id:String)
}