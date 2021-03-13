package ru.nds.poltavacrimea.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.nds.cabinet.info.CabinetInfoCoordinator
import ru.nds.planfix.coordinator.SetUpCoordinator
import ru.nds.poltavacrimea.navigation.GlobalCoordinator
import ru.nds.poltavacrimea.ui.MainActivityViewModelImpl
import ru.nds.poltavacrimea.ui.MainCoordinator

val appModule = module {
    viewModel {
        MainActivityViewModelImpl(
            notificationsManagerSetUp = get(),
            setUpCoordinator = get(),
            notificationsManager = get(),
            mainCoordinator = get(),
            gson = get(),
            appResources = get(),
        )
    }

    single { GlobalCoordinator() }

    factory<SetUpCoordinator> { get<GlobalCoordinator>() }
    factory<MainCoordinator> { get<GlobalCoordinator>() }
    factory<CabinetInfoCoordinator> { get<GlobalCoordinator>() }
    factory<ru.nds.planfix.scaner.ScannerCoordinator> { get<GlobalCoordinator>() }
}