package ru.nds.cabinet.info

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import ru.nds.models.*
import ru.nds.planfix.base.BaseScreenState
import ru.nds.planfix.base.BaseViewModelImpl
import ru.nds.planfix.base.FullScreenMessageData
import ru.nds.planfix.notifications.NotificationsManager
import ru.nds.planfix.resultcodes.CODE_SCANNED_RESULT
import ru.nds.planfix.scan.appResources.AppResources

class CabinetInfoViewModelImpl(
    private val cabinetInfoCoordinator: CabinetInfoCoordinator,
    private val notificationsManager: NotificationsManager,
    private val gson: Gson,
    private val appResources: AppResources,
) : BaseViewModelImpl(), CabinetInfoViewModel {
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
        requests.add(
            cabinetInfoCoordinator.addResultListener<String>(CODE_SCANNED_RESULT)
                .subscribe(::onCabinetScanned)
        )
        cabinetInfoCoordinator.openScanner()
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
        requests.add(
            cabinetInfoCoordinator.addResultListener<String>(CODE_SCANNED_RESULT)
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
        cabinetInfoCoordinator.openScanner()
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