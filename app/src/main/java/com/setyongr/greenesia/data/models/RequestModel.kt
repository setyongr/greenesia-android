package com.setyongr.greenesia.data.models

class LoginCredentials(
        val email: String,
        val password: String
)

class TakeEventRequest(
        val event: String,
        val status: Boolean = false
)

class TakeRewardRequest(
        val reward: String,
        val processed: Boolean = false
)

class AcceptAttendantRequest(
        val status: Boolean = true
)