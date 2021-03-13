package ru.nds.cabinet.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.nds.cabinet.info.databinding.DialogChangeCabinetScheduleStatusBinding
import ru.nds.models.CabinetScheduleItem
import ru.nds.planfix.binding.viewBinding

class ChangeCabinetScheduleStatusDialog : DialogFragment() {

    companion object {
        fun createInstance(
            cabinetScheduleItem: CabinetScheduleItem,
            listener: OnStatusChangedListener
        ): ChangeCabinetScheduleStatusDialog {
            return ChangeCabinetScheduleStatusDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(CABINET_SCHEDULE_ITEM_ARG, cabinetScheduleItem)
                }
                this.listener = listener
            }
        }

        private const val CABINET_SCHEDULE_ITEM_ARG = "CABINET_SCHEDULE_ITEM_ARG"
    }

    private var listener: OnStatusChangedListener? = null

    private val scheduleItem: CabinetScheduleItem
        get() = arguments?.getParcelable(
            CABINET_SCHEDULE_ITEM_ARG
        )!!

    private val binding: DialogChangeCabinetScheduleStatusBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_change_cabinet_schedule_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.time.text = scheduleItem.time
        binding.visit.setOnClickListener {
            listener?.onVisitClick()
            dismissAllowingStateLoss()
        }
        binding.missed.setOnClickListener {
            listener?.onMissedClick()
            dismissAllowingStateLoss()
        }
    }

    interface OnStatusChangedListener {
        fun onVisitClick()
        fun onMissedClick()
    }

}