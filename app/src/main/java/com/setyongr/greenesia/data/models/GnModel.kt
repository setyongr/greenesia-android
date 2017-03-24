package com.setyongr.greenesia.data.models

import com.setyongr.greenesia.common.adapters.AdapterConstant
import com.setyongr.greenesia.common.adapters.ViewType

class LocationData(
        val type: String,
        val coordinates: List<Double>
)
class EventData(
        val id: String,
        val nama: String,
        val gambar: String,
        val deskripsi: String,
        val tanggal: String,
        val alamat: String,
        val lokasi: LocationData
): ViewType {
    override fun getViewType(): Int {
        return AdapterConstant.EVENT
    }
}

class Event(
        val has_more: Boolean,
        val data: List<EventData>
)

// Reward
class RewardData(
        val id: String,
        val nama: String,
        val gambar: String,
        val min_point: Int
): ViewType{
    override fun getViewType(): Int {
        return AdapterConstant.REWARD
    }
}
class Reward(
        val has_more: Boolean,
        val data: List<RewardData>
)

// Take Event
class TakeEventData(
        val id: String,
        val status: Boolean,
        val user: UserData,
        val event: EventData
): ViewType{
    override fun getViewType(): Int {
        return AdapterConstant.TAKEEVENT
    }
}

class TakeEvent(
        val has_more: Boolean,
        val data: List<TakeEventData>
)


// Take Reward
class TakeRewardData(
        val id: String,
        val user: UserData,
        val reward: RewardData,
        val processed: Boolean
): ViewType{
    override fun getViewType(): Int {
        return AdapterConstant.TAKEREWARD
    }
}

class TakeReward(
        val has_more: Boolean,
        val data: List<TakeRewardData>
)


// User
class UserData(
        val id: String,
        val email: String,
        val nama: String,
        val alamat: String,
        val tipe: String,
        val message_tokem: String,
        val point: Int
)

class TokenResponse(
        val access_token: String,
        val refresh_token: String
)
