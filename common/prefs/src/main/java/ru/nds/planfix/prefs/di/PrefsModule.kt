package ru.nds.planfix.prefs.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.nds.planfix.prefs.CommonPrefsStorage
import ru.nds.planfix.prefs.ICommonPrefs

val prefsModule = module {
    factory<ICommonPrefs> {
        CommonPrefsStorage(androidContext())
    }
}