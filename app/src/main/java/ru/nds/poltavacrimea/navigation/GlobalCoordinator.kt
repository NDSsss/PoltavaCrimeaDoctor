package ru.nds.poltavacrimea.navigation

import android.util.SparseArray
import androidx.fragment.app.FragmentManager
import io.reactivex.rxjava3.core.Observable
import ru.nds.cabinet.info.CabinetInfoCoordinator
import ru.nds.cabinet.info.CabinetInfoFragment
import ru.nds.planfix.coordinator.SetUpCoordinator
import ru.nds.poltavacrimea.R
import ru.nds.poltavacrimea.ui.MainCoordinator
import ru.nds.shared.scanner.navigation.dto.ScannerDto

class GlobalCoordinator : SetUpCoordinator,
    ru.nds.planfix.scaner.ScannerCoordinator,
    MainCoordinator,
    CabinetInfoCoordinator {

    private var fm: FragmentManager? = null

    private val pendingOperations: MutableList<(FragmentManager) -> Unit> = mutableListOf()
    private val resultListeners = SparseArray<ResultListener>()

    override fun setFragmentManager(fm: FragmentManager) {
        this.fm = fm
        pendingOperations.forEach {
            it.invoke(fm)
        }
        pendingOperations.clear()
    }

    override fun removeFragmentManager() {
        fm = null
    }

    override fun back() {
        commitOnActiveFm { it.popBackStackImmediate() }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> addResultListener(code: Int): Observable<T> {
        val res: Observable<T> = Observable.create { subscriber ->
            resultListeners.put(
                code,
                object : ResultListener {
                    override fun onResult(resultData: Any?) {
                        (resultData as? T)?.let {
                            subscriber.onNext(it)
                        }
                    }
                }
            )
        }
        res.doOnDispose {
            removeResultListener(code)
        }
        return res
    }

    override fun removeResultListener(code: Int) {
        resultListeners.remove(code)
    }

    override fun sendResult(code: Int, result: Any?): Boolean {
        val resultListener = resultListeners[code]
        return if (resultListener != null) {
            resultListener.onResult(result)
            true
        } else {
            false
        }
    }

    override fun openCabinetScreen() {
        commitOnActiveFm {
            it.beginTransaction()
                .addToBackStack("openDoctorScreen")
                .replace(
                    R.id.container,
                    CabinetInfoFragment.newInstance(),
                    CabinetInfoFragment.TAG
                )
                .commit()
        }
    }

    override fun openScanner(scannerDto: ScannerDto) {
        commitOnActiveFm {
            it.beginTransaction()
                .addToBackStack("openScanner")
                .replace(
                    R.id.container,
                    ru.nds.planfix.scaner.ScannerFragment.newInstance(scannerDto),
                    ru.nds.planfix.scaner.ScannerFragment.TAG
                )
                .commit()
        }
    }

    private fun commitOnActiveFm(operation: (FragmentManager) -> Unit) {
        fm.apply {
            if (this != null) {
                operation.invoke(this)
            } else {
                pendingOperations.add(operation)
            }
        }
    }
}

interface ResultListener {
    fun onResult(resultData: Any?)
}