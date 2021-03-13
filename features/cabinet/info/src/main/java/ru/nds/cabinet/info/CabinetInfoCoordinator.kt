package ru.nds.cabinet.info

import ru.nds.planfix.coordinator.BaseCoordinator

interface CabinetInfoCoordinator: BaseCoordinator {
    fun openScanner()
}