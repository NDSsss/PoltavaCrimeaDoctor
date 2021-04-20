package ru.nds.cabinet.info

import android.os.Bundle
import android.view.View
import org.koin.android.viewmodel.ext.android.viewModel
import ru.nds.cabinet.info.databinding.FragCabinetInfoBinding
import ru.nds.models.CabinetData
import ru.nds.models.CabinetScheduleItem
import ru.nds.planfix.base.BaseFragment
import ru.nds.planfix.binding.viewBinding

class CabinetInfoFragment : BaseFragment<CabinetInfoViewModel, CabinetInfoViewModelImpl>(R.layout.frag_cabinet_info, CabinetInfoViewModelImpl::class) {

    companion object {
        const val TAG = "CabinetInfoFragment"
        fun newInstance() = CabinetInfoFragment()
    }

    private val binding: FragCabinetInfoBinding by viewBinding()

    private val cabinetSchedulesAdapter = CabinetScheduleAdapter {
        showChangeStatusDialog(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cabinetInfo.observe(viewLifecycleOwner, ::seCabinetData)
        binding.schedules.adapter = cabinetSchedulesAdapter
    }

    private fun seCabinetData(cabinetData: CabinetData) {
        when (cabinetData) {
            CabinetData.CabinetUnknown -> {
            }
            is CabinetData.CabinetSelected -> {
                binding.cabinetName.text = cabinetData.name
                cabinetSchedulesAdapter.schedules = cabinetData.schedules ?: listOf()
            }
        }
    }

    private fun showChangeStatusDialog(scheduleItem: CabinetScheduleItem) {
        ChangeCabinetScheduleStatusDialog.createInstance(
            scheduleItem,
            object : ChangeCabinetScheduleStatusDialog.OnStatusChangedListener {
                override fun onVisitClick() {
                    viewModel.onVisitedClick(scheduleItem)
                }

                override fun onMissedClick() {
                    viewModel.onMissedClick(scheduleItem)
                }

            }
        ).show(childFragmentManager, this::class.java.simpleName)
    }

    override fun onDestroyView() {
        binding.schedules.adapter = null
        super.onDestroyView()
    }
}