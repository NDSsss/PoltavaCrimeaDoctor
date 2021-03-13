package ru.nds.poltavacrimea

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import ru.nds.cabinet.info.di.cabinetInfoModule
import ru.nds.cabinet.info.di.doctorInfoModule
import ru.nds.planfix.appResources.di.appResourcesModule
import ru.nds.planfix.network.di.networkModule
import ru.nds.planfix.notifications.di.notificationsModule
import ru.nds.planfix.prefs.di.prefsModule
import ru.nds.planfix.scaner.di.scannerModule
import ru.nds.poltavacrimea.di.appModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
        loadKoinModules(appResourcesModule)
        loadKoinModules(notificationsModule)
        loadKoinModules(prefsModule)

        loadKoinModules(networkModule)

        loadKoinModules(scannerModule)
        loadKoinModules(doctorInfoModule)
        loadKoinModules(cabinetInfoModule)
    }
}