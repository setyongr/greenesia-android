package com.setyongr.greenesia.views.interfaces.organizer

import com.setyongr.greenesia.base.MvpView
import com.setyongr.greenesia.data.models.EventData

interface OrganizedDetailMvpView: MvpView {
    fun showDetail(data: EventData)
    fun showError(e: String?)
    fun showProgress()
    fun hideProgress()
}