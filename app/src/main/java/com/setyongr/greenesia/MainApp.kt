package com.setyongr.greenesia

import android.app.Application
import com.setyongr.greenesia.di.AppModule
import com.setyongr.greenesia.di.DaggerGreenesiaComponent
import com.setyongr.greenesia.di.GreenesiaComponent

class MainApp: Application(){
    companion object{
        lateinit var greenesiaComponent: GreenesiaComponent
    }

    override fun onCreate() {
        super.onCreate()
        greenesiaComponent = DaggerGreenesiaComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}