package com.setyongr.greenesia.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.setyongr.greenesia.data.models.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import rx.Observable

interface GnService {
    companion object {
        val ENDPOINT = "https://greenesia.herokuapp.com/"
    }

    @GET("event")
    fun getEvent(
            @Query("_limit") limit: Int = 10,
            @Query("_skip") skip: Int = 0,
            @Query("lokasi__geo_within_sphere") nearby: String? = null): Observable<Event>

    @GET("event/{id}")
    fun getEventDetail(@Path("id") id: String): Observable<EventData>

    @GET("reward")
    fun getReward(@Query("_limit") limit: Int = 10, @Query("_skip") skip: Int = 0):Observable<Reward>

    @POST("login/")
    fun login(@Body loginCredentials: LoginCredentials): Observable<TokenResponse>

    @GET("take_event")
    fun getTakeEvent(
            @Query("_limit") limit: Int = 10,
            @Query("_skip") skip: Int = 0
    ): Observable<TakeEvent>

    @POST("take_event/")
    fun postTakeEvent(@Body takeEventRequest: TakeEventRequest): Observable<TakeEventData>


    @GET("take_reward")
    fun getTakeReward(
            @Query("_limit") limit: Int = 10,
            @Query("_skip") skip: Int = 0
    ): Observable<TakeReward>

    @POST("take_reward/")
    fun postTakeReward(@Body takeRewardRequest: TakeRewardRequest): Observable<TakeRewardData>

    @GET("my/")
    fun myProfile():Observable<UserData>

    @GET("organizer/event")
    fun organizedEvent(
            @Query("_limit") limit: Int = 10,
            @Query("_skip") skip: Int = 0
    ): Observable<Event>

    @GET("organizer/attendant")
    fun getAttendant(
            @Query("_limit") limit: Int = 10,
            @Query("_skip") skip: Int = 0,
            @Query("event__exact") event: String
    ): Observable<TakeEvent>

    @PUT("organizer/attendant/{id}/")
    fun putAttendant(@Path("id") id: String, @Body acceptAttendantRequest: AcceptAttendantRequest): Observable<TakeEventData>
}