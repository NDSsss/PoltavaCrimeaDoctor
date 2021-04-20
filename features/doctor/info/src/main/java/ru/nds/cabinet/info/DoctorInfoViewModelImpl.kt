package ru.nds.cabinet.info

import ru.nds.planfix.base.BaseViewModelImpl
import ru.nds.planfix.notifications.NotificationsManager
import ru.nds.planfix.scan.appResources.AppResources

class DoctorInfoViewModelImpl(
    appResources: AppResources,
    notificationsManager: NotificationsManager,
) : BaseViewModelImpl(appResources, notificationsManager), DoctorInfoViewModel {
}