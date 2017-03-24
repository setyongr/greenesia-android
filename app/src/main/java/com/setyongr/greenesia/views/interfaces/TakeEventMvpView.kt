package com.setyongr.greenesia.views.interfaces

import com.setyongr.greenesia.base.MvpView
import com.setyongr.greenesia.data.models.Reward
import com.setyongr.greenesia.data.models.TakeEvent

interface TakeEventMvpView: MvpView{
    fun showList(data: TakeEvent)
    fun showError(e: String?)
}