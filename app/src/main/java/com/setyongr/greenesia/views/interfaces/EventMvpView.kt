package com.setyongr.greenesia.views.interfaces

import com.setyongr.greenesia.base.MvpView
import com.setyongr.greenesia.data.models.Event
import com.setyongr.greenesia.data.models.EventData

interface EventMvpView : MvpView {
    fun showList(data: Event)
    fun showError(e: String?)
}