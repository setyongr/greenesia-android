package com.setyongr.greenesia.data

import com.setyongr.greenesia.data.models.*
import io.realm.Realm
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor(private val gnService: GnService){
    fun getEvent(limit: Int = 10, skip: Int = 0, nearby: String? = null):Observable<Event>
            = gnService.getEvent(limit, skip, nearby)
    fun getEventDetail(id: String):Observable<EventData>
            = gnService.getEventDetail(id)
    fun getReward(limit: Int = 10, skip: Int = 0):Observable<Reward>
            = gnService.getReward(limit, skip)
    fun login(email: String, password: String):Observable<TokenResponse>
            = gnService.login(LoginCredentials(email, password))

    fun getTakeEvent(limit: Int = 10, skip: Int = 0): Observable<TakeEvent>
            = gnService.getTakeEvent(limit, skip)
    fun takeEvent(id: String): Observable<TakeEventData>
            = gnService.postTakeEvent(TakeEventRequest(id))

    fun getTakeReward(limit: Int = 10, skip: Int = 0): Observable<TakeReward>
            = gnService.getTakeReward(limit, skip)

    fun takeReward(id: String): Observable<TakeRewardData>
            = gnService.postTakeReward(TakeRewardRequest(id))

    fun myProfile():Observable<UserData>
            = gnService.myProfile()

    fun getProfile():RmUser {
        val realm = Realm.getDefaultInstance()
        return realm.where(RmUser::class.java).findFirst()
    }

    fun organizedEvent(limit: Int = 10, skip: Int = 0):Observable<Event>
            = gnService.organizedEvent(limit, skip)

    fun getAttendant(limit: Int = 10, skip: Int = 0, event: String): Observable<TakeEvent>
            = gnService.getAttendant(limit, skip, event)

    fun acceptAttendant(id: String): Observable<TakeEventData>
            = gnService.putAttendant(id, AcceptAttendantRequest(true))
}