package ru.nds.cabinet.info

import ru.nds.cabinet.info.databinding.FragDoctorInfoBinding
import ru.nds.planfix.base.BaseFragment
import ru.nds.planfix.binding.viewBinding

class DoctorInfoFragment :
    BaseFragment<DoctorInfoViewModel, DoctorInfoViewModelImpl>(R.layout.frag_doctor_info, DoctorInfoViewModelImpl::class) {

    companion object {
        const val TAG = "DoctorInfoFragment"
        fun newInstance() = DoctorInfoFragment()
    }

    private val viewBinding: FragDoctorInfoBinding by viewBinding()
}