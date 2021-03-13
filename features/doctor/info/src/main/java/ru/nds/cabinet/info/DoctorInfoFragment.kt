package ru.nds.cabinet.info

import org.koin.android.viewmodel.ext.android.viewModel
import ru.nds.cabinet.info.databinding.FragDoctorInfoBinding
import ru.nds.planfix.base.BaseFragment
import ru.nds.planfix.binding.viewBinding

class DoctorInfoFragment : BaseFragment<DoctorInfoViewModel>(R.layout.frag_doctor_info) {

    companion object {
        const val TAG = "DoctorInfoFragment"
        fun newInstance() = DoctorInfoFragment()
    }

    override val viewModel: DoctorInfoViewModel by viewModel<DoctorInfoViewModelImpl>()
    private val viewBinding: FragDoctorInfoBinding by viewBinding()
}