package com.setyongr.greenesia.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.setyongr.greenesia.R
import com.setyongr.greenesia.common.adapters.ViewType
import com.setyongr.greenesia.common.adapters.ViewTypeDelegateAdapter
import com.setyongr.greenesia.common.inflate

class LoadingDelegateAdapter: ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder(parent)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {

    }

    class LoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_loading)) {
    }

}