package ru.nds.cabinet.info.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.nds.cabinet.info.CabinetInfoViewModelImpl

val cabinetInfoModule = module {
    viewModel {
        CabinetInfoViewModelImpl(
            cabinetInfoCoordinator = get(),
            notificationsManager = get(),
            gson = get(),
            appResources = get(),
        )
    }
}