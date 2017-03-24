package com.setyongr.greenesia.views.interfaces.organizer

import com.setyongr.greenesia.base.MvpView
import com.setyongr.greenesia.data.models.Event
import com.setyongr.greenesia.data.models.TakeEvent
import com.setyongr.greenesia.data.models.TakeReward

interface EventListMvpView: MvpView{
    fun showList(data: Event)
    fun showError(e: String?)
}