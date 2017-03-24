package com.setyongr.greenesia.views.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.setyongr.greenesia.R
import com.setyongr.greenesia.common.adapters.ViewType
import com.setyongr.greenesia.common.adapters.ViewTypeDelegateAdapter
import com.setyongr.greenesia.common.inflate
import com.setyongr.greenesia.common.loadImg
import com.setyongr.greenesia.data.GnService
import com.setyongr.greenesia.data.models.TakeEventData
import com.setyongr.greenesia.views.TakeEventDetailActivity
import kotlinx.android.synthetic.main.event_item.view.*


class TakeEventDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as DiscoverViewHolder).bind(item as TakeEventData)
    }

    override fun onCreateViewHolder(parent: ViewGroup) = DiscoverViewHolder(parent)

    class DiscoverViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.event_item)) {
        fun bind(item: TakeEventData) = with(itemView) {
            nama.text = item.event.nama
            deskripsi.text = item.event.deskripsi
            banner.loadImg(GnService.ENDPOINT + "static/" + item.event.gambar)

            setOnClickListener {
                val intent = Intent(context, TakeEventDetailActivity::class.java)
                intent.putExtra("id", item.id)
                context.startActivity(intent)
            }
        }
    }
}

