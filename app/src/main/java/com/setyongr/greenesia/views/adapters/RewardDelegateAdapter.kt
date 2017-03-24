package com.setyongr.greenesia.views.adapters

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.setyongr.greenesia.R
import com.setyongr.greenesia.common.adapters.ViewType
import com.setyongr.greenesia.common.adapters.ViewTypeDelegateAdapter
import com.setyongr.greenesia.common.inflate
import com.setyongr.greenesia.common.loadImg
import com.setyongr.greenesia.data.DataManager
import com.setyongr.greenesia.data.GnService
import com.setyongr.greenesia.data.models.RewardData
import kotlinx.android.synthetic.main.reward_item.view.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class RewardDelegateAdapter constructor(val dataManager: DataManager): ViewTypeDelegateAdapter {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as DiscoverViewHolder).bind(item as RewardData)
    }

    override fun onCreateViewHolder(parent: ViewGroup) = DiscoverViewHolder(parent, dataManager)

    class DiscoverViewHolder(parent: ViewGroup, val dataManager: DataManager): RecyclerView.ViewHolder(parent.inflate(R.layout.reward_item)) {
        private var  dialog: ProgressDialog? = null

        fun bind(item: RewardData) = with(itemView) {
            nama.text = item.nama
            deskripsi.text = "Poin Minimal: " + item.min_point.toString()
            banner.loadImg(GnService.ENDPOINT + "static/" + item.gambar)
            btnTakeReward.setOnClickListener {

                if (dataManager.getProfile().point!! < item.min_point){
                   AlertDialog.Builder(context).setTitle("Maaf").setMessage("Point Tidak Mencukupi").show()
                }else{
                    dialog = ProgressDialog.show(context, "",
                            "Loading. Please wait...", true)
                    dataManager.takeReward(item.id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    {
                                        received ->
                                        dialog?.dismiss()
                                        AlertDialog.Builder(context).setTitle("Sukses!").setMessage("Reward Anda Sedang Di Proses").show()
                                    },
                                    Throwable::printStackTrace
                            )
                }

            }

        }
    }
}
