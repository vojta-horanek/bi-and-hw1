package cz.cvut.fit.biand.homework1.presentation

import android.app.Application
import cz.cvut.fit.biand.homework1.di.appModule
import cz.cvut.fit.biand.homework1.di.dataModule
import cz.cvut.fit.biand.homework1.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(appModule, networkModule, dataModule)
        }
    }
}