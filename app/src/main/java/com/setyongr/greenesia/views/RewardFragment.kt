package com.setyongr.greenesia.views

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setyongr.greenesia.MainApp
import com.setyongr.greenesia.R
import com.setyongr.greenesia.common.InfiniteScrollListener
import com.setyongr.greenesia.common.inflate
import com.setyongr.greenesia.data.DataManager
import com.setyongr.greenesia.data.models.Reward
import com.setyongr.greenesia.presenters.RewardPresenter
import com.setyongr.greenesia.views.adapters.RewardAdapter
import com.setyongr.greenesia.views.interfaces.RewardMvpView
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class RewardFragment: Fragment(), RewardMvpView{
    @Inject lateinit var mRewardPresenter: RewardPresenter
    @Inject lateinit var dataManager: DataManager

    companion object{
        fun newInstance(): RewardFragment {
            val fragment = RewardFragment()
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = container?.inflate(R.layout.fragment_list)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainApp.greenesiaComponent.inject(this)

        mRewardPresenter.attachView(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        data_list.setHasFixedSize(true)
        val linearLayout = LinearLayoutManager(context)
        data_list.layoutManager = linearLayout
        data_list.clearOnScrollListeners()
        data_list.addOnScrollListener(InfiniteScrollListener({ mRewardPresenter.loadReward()}, linearLayout))

        initAdapter()

        if (savedInstanceState == null) {
            mRewardPresenter.loadReward()
        }
    }


    override fun showList(data: Reward) {
        (data_list.adapter as RewardAdapter).addItem(data)
    }

    override fun showError(e: String?) {
        Snackbar.make(data_list, e ?: "", Snackbar.LENGTH_LONG).show()
    }

    private fun initAdapter() {
        if (data_list.adapter == null) {
            data_list.adapter = RewardAdapter(dataManager)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRewardPresenter.detachView()
    }
}