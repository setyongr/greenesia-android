package com.setyongr.greenesia.common.services

import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.messaging.FirebaseMessaging

class AppFirebaseInstanceIDService :FirebaseInstanceIdService(){
    override fun onCreate() {
        super.onCreate()
    }
    override fun onTokenRefresh() {
        FirebaseMessaging.getInstance().subscribeToTopic("accident")
    }
}