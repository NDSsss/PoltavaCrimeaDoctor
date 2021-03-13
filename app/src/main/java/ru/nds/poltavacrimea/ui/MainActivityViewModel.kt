package ru.nds.poltavacrimea.ui

import android.app.Activity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import ru.nds.models.DoctorData

interface MainActivityViewModel {

    val doctorInfo: LiveData<DoctorData>

    fun onCameraPermissionsGranted()
    fun onScanCabinetClick()

    fun setFragmentManager(fm: FragmentManager)
    fun removeFragmentManager()
    fun setActivity(activity: Activity)
    fun removeActivity()
}