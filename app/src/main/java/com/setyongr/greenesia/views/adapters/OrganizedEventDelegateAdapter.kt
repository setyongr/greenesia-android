package com.setyongr.greenesia.views.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.setyongr.greenesia.views.DetailActivity
import com.setyongr.greenesia.R
import com.setyongr.greenesia.common.adapters.ViewType
import com.setyongr.greenesia.common.adapters.ViewTypeDelegateAdapter
import com.setyongr.greenesia.common.inflate
import com.setyongr.greenesia.common.loadImg
import com.setyongr.greenesia.data.GnService
import com.setyongr.greenesia.data.models.EventData
import com.setyongr.greenesia.views.organizer.OrganizedEventDetailActivity
import kotlinx.android.synthetic.main.event_item.view.*

class OrganizedEventDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as DiscoverViewHolder).bind(item as EventData)
    }

    override fun onCreateViewHolder(parent: ViewGroup) = DiscoverViewHolder(parent)

    class DiscoverViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.event_item)) {
        fun bind(item: EventData) = with(itemView) {
            nama.text = item.nama
            deskripsi.text = item.deskripsi
            banner.loadImg(GnService.ENDPOINT + "static/" + item.gambar)

            setOnClickListener {
                val intent = Intent(context, OrganizedEventDetailActivity::class.java)
                intent.putExtra("id", item.id)
                context.startActivity(intent)
            }
//            banner.loadImg("https://image.tmdb.org/t/p/w780" + item.backdrop_path)
//            title.text = item.title
//            release.text = item.release_date
//            setOnClickListener {
//                val intent = Intent(context, DetailActivity::class.java)
//                intent.putExtra("id", item.id)
//                context.startActivity(intent)
//            }
        }
    }
}

