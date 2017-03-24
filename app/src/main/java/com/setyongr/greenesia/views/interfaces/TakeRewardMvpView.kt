package com.setyongr.greenesia.views.interfaces

import com.setyongr.greenesia.base.MvpView
import com.setyongr.greenesia.data.models.TakeReward

interface TakeRewardMvpView: MvpView{
    fun showList(data: TakeReward)
    fun showError(e: String?)
}