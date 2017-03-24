package com.setyongr.greenesia.views.interfaces

import com.setyongr.greenesia.base.MvpView
import com.setyongr.greenesia.data.models.Reward
import com.setyongr.greenesia.data.models.RewardData

interface RewardMvpView: MvpView{
    fun showList(data: Reward)
    fun showError(e: String?)
}