package com.setyongr.greenesia.views.adapters

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.setyongr.greenesia.common.adapters.AdapterConstant
import com.setyongr.greenesia.common.adapters.ViewType
import com.setyongr.greenesia.common.adapters.ViewTypeDelegateAdapter
import com.setyongr.greenesia.data.models.TakeEvent
import com.setyongr.greenesia.data.models.TakeEventData

import java.util.*


class TakeEventAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<ViewType> = ArrayList()
    private var delegatedAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    private var loadingItem = object : ViewType {
        override fun getViewType() = AdapterConstant.LOADING

    }

    init {
        delegatedAdapters.put(AdapterConstant.LOADING, LoadingDelegateAdapter())
        delegatedAdapters.put(AdapterConstant.TAKEEVENT, TakeEventDelegateAdapter())
        items.add(loadingItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatedAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatedAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return this.items.get(position).getViewType()
    }
    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(data: TakeEvent) {
        // remove loading
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        // Insert data and loading
        items.addAll(data.data)
        if(data.has_more){
            items.add(loadingItem)
            notifyItemRangeChanged(initPosition, items.size + 1 /* Plus loading item*/)
        }else{
            notifyItemRangeChanged(initPosition, items.size)
        }
    }

    fun clearAndAddItem(data: List<TakeEventData>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        items.addAll(data)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
    }

    fun getNews(): List<TakeEventData> {
        return items
                .filter { it.getViewType() == AdapterConstant.TAKEEVENT }
                .map{ it as TakeEventData }
    }
    fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}