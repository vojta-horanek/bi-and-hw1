package cz.cvut.fit.biand.homework1.presentation

import android.app.Application
import cz.cvut.fit.biand.homework1.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                networkModule,
                infrastructureModule,
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}