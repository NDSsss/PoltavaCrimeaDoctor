package ru.nds.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class CabinetData {
    object CabinetUnknown : CabinetData()
    @Parcelize
    data class CabinetSelected(
        val name: String,
        val schedules: List<CabinetScheduleItem>?
    ) : CabinetData(), Parcelable
}

@Parcelize
data class CabinetScheduleItem(
    val id: Int,
    val time: String,
    val clientName: String,
    val description: String,
    val status: CabinetScheduleStatus = CabinetScheduleStatus.Unknown
) : Parcelable

sealed class CabinetScheduleStatus: Parcelable {
    @Parcelize
    object Unknown : CabinetScheduleStatus()
    @Parcelize
    object Visited : CabinetScheduleStatus()
    @Parcelize
    object Missed : CabinetScheduleStatus()
}

fun generateMockCabinetSchedule(): MutableList<CabinetScheduleItem> = mutableListOf(
    CabinetScheduleItem(
        id = 0,
        time = "09:00",
        clientName = "Иванов Иван Иванович",
        description = "Массаж шеи"
    ),
    CabinetScheduleItem(
        id = 1,
        time = "10:00",
        clientName = "Свободно",
        description = ""
    ),
    CabinetScheduleItem(
        id = 2,
        time = "11:00",
        clientName = "Иванов Иван Иванович",
        description = "Массаж шеи"
    ),
    CabinetScheduleItem(
        id = 3,
        time = "12:00",
        clientName = "Иванов Иван Иванович",
        description = "Массаж шеи"
    ),
    CabinetScheduleItem(
        id = 4,
        time = "13:00",
        clientName = "Обед",
        description = ""
    ),
    CabinetScheduleItem(
        id = 5,
        time = "14:00",
        clientName = "Иванов Иван Иванович",
        description = "Массаж шеи"
    ),
    CabinetScheduleItem(
        id = 6,
        time = "15:00",
        clientName = "Свободно",
        description = ""
    ),
    CabinetScheduleItem(
        id = 7,
        time = "16:00",
        clientName = "Иванов Иван Иванович",
        description = "Массаж шеи"
    ),
    CabinetScheduleItem(
        id = 8,
        time = "17:00",
        clientName = "Иванов Иван Иванович",
        description = "Массаж шеи"
    ),
    CabinetScheduleItem(
        id = 9,
        time = "18:00",
        clientName = "Свободно",
        description = ""
    ),
)