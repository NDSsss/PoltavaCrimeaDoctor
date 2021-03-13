package ru.nds.cabinet.info

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.nds.cabinet.info.databinding.ItemCabinetSchedueBinding
import ru.nds.models.CabinetScheduleItem
import ru.nds.models.CabinetScheduleStatus

class CabinetScheduleAdapter(
    private val onScheduleClickListener: (CabinetScheduleItem) -> Unit
) : RecyclerView.Adapter<CabinetScheduleVh>() {

    var schedules: List<CabinetScheduleItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CabinetScheduleVh =
        CabinetScheduleVh(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cabinet_schedue, parent, false)
        )

    override fun onBindViewHolder(holder: CabinetScheduleVh, position: Int) {
        holder.bind(schedules[position], onScheduleClickListener)
    }

    override fun getItemCount(): Int = schedules.size
}

class CabinetScheduleVh(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(
        schedule: CabinetScheduleItem,
        onScheduleClickListener: (CabinetScheduleItem) -> Unit
    ) {
        val binding = ItemCabinetSchedueBinding.bind(itemView)
        binding.time.text = schedule.time
        binding.clientName.text = schedule.clientName
        binding.procedure.text = schedule.description
        val bgColorRes = when (schedule.status) {
            CabinetScheduleStatus.Unknown -> android.R.color.transparent
            CabinetScheduleStatus.Visited -> R.color.schedule_visited
            CabinetScheduleStatus.Missed -> R.color.schedule_missed
        }
        Log.d("APP_TAG", "${this::class.java.simpleName} ${this::class.java.hashCode()} bgColorRes: $bgColorRes");
        val color = ContextCompat.getColor(binding.root.context, bgColorRes)
        binding.root.setBackgroundResource(bgColorRes)
        binding.root.setOnClickListener {
            onScheduleClickListener(schedule)
        }
    }
}