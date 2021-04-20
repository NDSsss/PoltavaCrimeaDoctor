package ru.nds.cabinet.info

import ru.nds.planfix.coordinator.BaseCoordinator
import ru.nds.shared.scanner.navigation.dto.ScannerDto

interface CabinetInfoCoordinator : BaseCoordinator {
    fun openScanner(scannerDto: ScannerDto)
}