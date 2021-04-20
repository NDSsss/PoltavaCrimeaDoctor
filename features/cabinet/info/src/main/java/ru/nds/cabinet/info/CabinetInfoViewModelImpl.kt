package ru.nds.cabinet.info

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import ru.nds.models.*
import ru.nds.planfix.base.BaseScreenState
import ru.nds.planfix.base.BaseViewModelImpl
import ru.nds.planfix.base.FullScreenMessageData
import ru.nds.planfix.notifications.NotificationsManager
import ru.nds.planfix.scan.appResources.AppResources
import ru.nds.shared.scanner.navigation.dto.ScannerDto
import ru.nds.shared.scanner.navigation.dto.ScannerMockDto

class CabinetInfoViewModelImpl(
    appResources: AppResources,
    notificationsManager: NotificationsManager,
    private val cabinetInfoCoordinator: CabinetInfoCoordinator,
    private val gson: Gson,
) : BaseViewModelImpl(appResources, notificationsManager), CabinetInfoViewModel {

    companion object {
        private const val SCAN_CABINET_CODE = 123
        private const val SCAN_VISIT_CODE = 124
    }

    override val cabinetInfo: MutableLiveData<CabinetData> = MutableLiveData()

    private val cabinetSchedulers: MutableList<CabinetScheduleItem> = generateMockCabinetSchedule()

    init {
        screenState.value = BaseScreenState.FullScreenMessage(
            messageData = FullScreenMessageData(
                titleRes = R.string.cabinet_unknown,
                actionNameRes = R.string.cabinet_scan_action,
                action = ::onScanCabinetClick
            )
        )
    }

    private fun onScanCabinetClick() {
        disposables.add(
            cabinetInfoCoordinator.addResultListener<String>(SCAN_CABINET_CODE)
                .subscribe(::onCabinetScanned)
        )
        cabinetInfoCoordinator.openScanner(
            ScannerDto(
                requestCode = SCAN_CABINET_CODE, null,
                mock = ScannerMockDto(
                    mockResult = gson.toJson(CabinetData.CabinetSelected(name = "305 Массаж", null))
                )
            )
        )
    }

    private fun onCabinetScanned(data: String) {
        val cabinetData: CabinetData.CabinetSelected? = try {
            gson.fromJson(data, CabinetData.CabinetSelected::class.java)
        } catch (e: Exception) {
            null
        }

        if (cabinetData == null) {
            notificationsManager.showNotification(appResources.getString(R.string.error_cabinet_scan))
            return
        }

        screenState.value = BaseScreenState.ShowContent
        cabinetInfo.value = cabinetData.let {
            if (it.schedules == null) {
                it.copy(
                    schedules = cabinetSchedulers
                )
            } else {
                it
            }
        }
    }

    override fun onVisitedClick(scheduleItem: CabinetScheduleItem) {
        disposables.add(
            cabinetInfoCoordinator.addResultListener<String>(SCAN_VISIT_CODE)
                .subscribe { scannedName ->
                    val clientName = try {
                        gson.fromJson(scannedName, ClientData::class.java).name
                    } catch (e: Exception) {
                        ""
                    }
                    val indexOfChange = cabinetSchedulers.indexOfFirst { it.id == scheduleItem.id }
                    cabinetSchedulers[indexOfChange] =
                        scheduleItem.copy(
                            clientName = clientName,
                            status = CabinetScheduleStatus.Visited
                        )
                    cabinetInfo.value = cabinetInfo.value?.let { cabinetData ->
                        when (cabinetData) {
                            CabinetData.CabinetUnknown -> cabinetData
                            is CabinetData.CabinetSelected -> cabinetData.copy(
                                schedules = cabinetSchedulers
                            )
                        }
                    }
                }
        )
        cabinetInfoCoordinator.openScanner(
            ScannerDto(
                requestCode = SCAN_VISIT_CODE,
                description = null,
                mock = ScannerMockDto(
                    mockResult = gson.toJson(ClientData(name = "Кухта Дмитирй"))
                )
            )
        )
    }

    override fun onMissedClick(scheduleItem: CabinetScheduleItem) {
        val indexOfChange = cabinetSchedulers.indexOfFirst { it.id == scheduleItem.id }
        cabinetSchedulers[indexOfChange] = scheduleItem.copy(status = CabinetScheduleStatus.Missed)
        cabinetInfo.value = cabinetInfo.value?.let {
            when (it) {
                CabinetData.CabinetUnknown -> it
                is CabinetData.CabinetSelected -> it.copy(schedules = cabinetSchedulers)
            }
        }
    }
}