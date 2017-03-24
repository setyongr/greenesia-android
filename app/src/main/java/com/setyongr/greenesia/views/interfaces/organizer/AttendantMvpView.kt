package com.setyongr.greenesia.views.interfaces.organizer

import com.setyongr.greenesia.base.MvpView
import com.setyongr.greenesia.data.models.Event
import com.setyongr.greenesia.data.models.TakeEvent

interface AttendantMvpView : MvpView {
    fun showList(data: TakeEvent)
    fun showError(e: String?)
}