package ru.nds.cabinet.info.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.nds.cabinet.info.DoctorInfoViewModelImpl

val doctorInfoModule = module {
    viewModel {
        DoctorInfoViewModelImpl(
            appResources = get(),
            notificationsManager = get()
        )
    }
}