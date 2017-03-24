package com.setyongr.greenesia.data.models

import io.realm.RealmObject

open class RmUser: RealmObject () {
    open var id: String? = null
    open var email: String? = null
    open var nama: String? = null
    open var alamat: String? = null
    open var tipe: String? = null
    open var message_tokem: String? = null
    open var point: Int? = null
}