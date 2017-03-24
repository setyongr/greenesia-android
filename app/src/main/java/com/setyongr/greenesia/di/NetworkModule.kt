package com.setyongr.greenesia.di

import android.content.Context
import com.setyongr.greenesia.data.GnService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import android.preference.PreferenceManager
import android.content.SharedPreferences
import com.setyongr.greenesia.common.AppPreferences


@Module
class NetworkModule{
    @Provides
    @Singleton
    fun provideRetrofit(context: Context): Retrofit {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val token: String? = sharedPreferences.getString(AppPreferences.AUTH_TOKEN, null)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor {
            chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder().method(original.method(), original.body())

            if(token != null){
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            val request = requestBuilder.build()
            chain.proceed(request)

        }

        val retrofit = Retrofit.Builder()
                .baseUrl(GnService.ENDPOINT)
                .client(httpClient.build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        return retrofit
    }

    @Provides
    @Singleton
    fun provideGnService(retrofit: Retrofit):GnService {
        return retrofit.create(GnService::class.java)
    }
}