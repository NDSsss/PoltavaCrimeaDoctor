package ru.nds.cabinet.info

import androidx.lifecycle.LiveData
import ru.nds.models.CabinetData
import ru.nds.models.CabinetScheduleItem
import ru.nds.planfix.base.BaseViewModel

interface CabinetInfoViewModel: BaseViewModel {
    val cabinetInfo: LiveData<CabinetData>

    fun onVisitedClick(scheduleItem: CabinetScheduleItem)
    fun onMissedClick(scheduleItem: CabinetScheduleItem)
}