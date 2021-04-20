package ru.nds.poltavacrimea.ui

import ru.nds.shared.scanner.navigation.dto.ScannerDto

interface MainCoordinator : ru.nds.planfix.coordinator.BaseCoordinator {
    fun openCabinetScreen()
    fun openScanner(scannerDto: ScannerDto)
}