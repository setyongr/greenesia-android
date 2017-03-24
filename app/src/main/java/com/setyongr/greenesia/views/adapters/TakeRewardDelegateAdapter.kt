package com.setyongr.greenesia.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.setyongr.greenesia.R
import com.setyongr.greenesia.common.adapters.ViewType
import com.setyongr.greenesia.common.adapters.ViewTypeDelegateAdapter
import com.setyongr.greenesia.common.inflate
import com.setyongr.greenesia.common.loadImg
import com.setyongr.greenesia.data.GnService
import com.setyongr.greenesia.data.models.TakeRewardData
import kotlinx.android.synthetic.main.reward_item.view.*


class TakeRewardDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as DiscoverViewHolder).bind(item as TakeRewardData)
    }

    override fun onCreateViewHolder(parent: ViewGroup) = DiscoverViewHolder(parent)

    class DiscoverViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.event_item)) {
        fun bind(item: TakeRewardData) = with(itemView) {
            nama.text = item.reward.nama
            deskripsi.text = "Status Pengiriman: " + if (item.processed) "Dikirim" else "Di Proses"
            banner.loadImg(GnService.ENDPOINT + "static/" + item.reward.gambar)

        }
    }
}

