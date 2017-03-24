package com.setyongr.greenesia.views.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.setyongr.greenesia.R
import com.setyongr.greenesia.common.adapters.ViewType
import com.setyongr.greenesia.common.adapters.ViewTypeDelegateAdapter
import com.setyongr.greenesia.common.inflate
import com.setyongr.greenesia.data.models.TakeEventData
import kotlinx.android.synthetic.main.attendant_item.view.*


class AttendantDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as DiscoverViewHolder).bind(item as TakeEventData)
    }

    override fun onCreateViewHolder(parent: ViewGroup) = DiscoverViewHolder(parent)

    class DiscoverViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.attendant_item)) {
        fun bind(item: TakeEventData) = with(itemView) {
            textNama.text = item.user.nama
            textEmail.text = item.user.email
            if (item.status){
                imgChecked.visibility = View.VISIBLE
            }
        }
    }
}

