package com.setyongr.greenesia.views.interfaces

import com.setyongr.greenesia.base.MvpView
import com.setyongr.greenesia.data.models.EventData

interface EventMapMvpView:MvpView{
    fun drawMarker(data: List<EventData>)
}