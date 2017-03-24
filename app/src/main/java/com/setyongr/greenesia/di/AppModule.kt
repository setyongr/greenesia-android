package com.setyongr.greenesia.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.setyongr.greenesia.MainApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: MainApp){
    @Provides
    @Singleton
    fun provideContext():Context{
        return app
    }

    @Provides
    @Singleton
    fun provideApplication():MainApp{
        return app
    }

    @Provides
    @Singleton
    fun provideSharedPref(context: Context):SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}