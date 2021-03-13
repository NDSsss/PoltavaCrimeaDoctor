package ru.nds.models

sealed class DoctorData {
    object DoctorUnknown : DoctorData()
    data class DoctorSelected(
        val name: String
    ) : DoctorData()
}