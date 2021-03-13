package ru.nds.poltavacrimea.ui

import android.app.Activity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.nds.models.DoctorData
import ru.nds.planfix.coordinator.SetUpCoordinator
import ru.nds.planfix.notifications.NotificationsManager
import ru.nds.planfix.notifications.NotificationsManagerSetUp
import ru.nds.planfix.resultcodes.CODE_SCANNED_RESULT
import ru.nds.planfix.scan.appResources.AppResources
import ru.nds.poltavacrimea.R

class MainActivityViewModelImpl(
    private val setUpCoordinator: SetUpCoordinator,
    private val notificationsManagerSetUp: NotificationsManagerSetUp,
    private val mainCoordinator: MainCoordinator,
    private val notificationsManager: NotificationsManager,
    private val gson: Gson,
    private val appResources: AppResources,
) : ViewModel(), MainActivityViewModel {

    private val requests = CompositeDisposable()

    override val doctorInfo: MutableLiveData<DoctorData> =
        MutableLiveData(DoctorData.DoctorUnknown)

    override fun onCameraPermissionsGranted() {
        mainCoordinator.openCabinetScreen()
    }

    override fun onScanCabinetClick() {
        requests.add(
            mainCoordinator.addResultListener<String>(CODE_SCANNED_RESULT)
                .subscribe(::onCabinetScanned)
        )
        mainCoordinator.openScanner()
    }

    private fun onCabinetScanned(data: String) {
        val doctorData: DoctorData.DoctorSelected? = try {
            gson.fromJson(data, DoctorData.DoctorSelected::class.java)
        } catch (e: Exception) {
            null
        }

        if(doctorData == null){
            notificationsManager.showNotification(appResources.getString(R.string.doctor_unknown))
            return
        }

        doctorInfo.value = doctorData
    }

    override fun setFragmentManager(fm: FragmentManager) {
        setUpCoordinator.setFragmentManager(fm)
    }

    override fun removeFragmentManager() {
        setUpCoordinator.removeFragmentManager()
    }

    override fun setActivity(activity: Activity) {
        notificationsManagerSetUp.setActivity(activity)
    }

    override fun removeActivity() {
        notificationsManagerSetUp.removeActivity()
    }
}