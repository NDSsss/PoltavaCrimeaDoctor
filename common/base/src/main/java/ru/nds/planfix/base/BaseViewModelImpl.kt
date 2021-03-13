package ru.nds.planfix.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModelImpl : ViewModel(), BaseViewModel {
    override val screenState: MutableLiveData<ScreenState> =
        MutableLiveData()
//        MutableLiveData(BaseScreenState.ShowContent)
//        MutableLiveData(BaseScreenState.Loading)
//        MutableLiveData(BaseScreenState.FullScreenMessage.createTestMessage())

    protected val requests = CompositeDisposable()

    override fun onCleared() {
        requests.dispose()
        super.onCleared()
    }
}