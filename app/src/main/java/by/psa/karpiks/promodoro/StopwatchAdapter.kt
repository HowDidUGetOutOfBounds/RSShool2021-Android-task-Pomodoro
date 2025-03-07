package by.psa.karpiks.promodoro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import by.psa.karpiks.promodoro.databinding.StopwatchItemBinding


class StopwatchAdapter(private val listener: StopwatchListener): ListAdapter<Stopwatch, StopWatchViewHolder>(itemComparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopWatchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = StopwatchItemBinding.inflate(layoutInflater, parent, false)
        return StopWatchViewHolder(binding,listener,binding.root.context.resources)
    }


    override fun onBindViewHolder(holder: StopWatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<Stopwatch>() {

            override fun areItemsTheSame(oldItem: Stopwatch, newItem: Stopwatch): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Stopwatch, newItem: Stopwatch): Boolean {
                return oldItem.currentMs == newItem.currentMs &&
                        oldItem.isStarted == newItem.isStarted
            }
        }
    }
}