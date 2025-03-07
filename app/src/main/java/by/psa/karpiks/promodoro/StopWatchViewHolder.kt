package by.psa.karpiks.promodoro

import android.content.res.Resources
import android.graphics.drawable.AnimationDrawable
import android.os.CountDownTimer
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import by.psa.karpiks.promodoro.databinding.StopwatchItemBinding

class StopWatchViewHolder(
    private val binding: StopwatchItemBinding,
    private val listener: StopwatchListener,
    private val resources: Resources
): RecyclerView.ViewHolder(binding.root) {

    private var timer: CountDownTimer? = null

    fun bind(stopwatch: Stopwatch){
        binding.stopwatchTimer.text = stopwatch.currentMs.displayTime()
        //show initial visuals
        binding.timeLeftView.setPeriod(stopwatch.allTime)
        binding.timeLeftView.setCurrent(stopwatch.allTime - stopwatch.currentMs)

        if(stopwatch.isStarted)
        {
            startTimer(stopwatch)
        }
        else{
            stopTimer(stopwatch)
        }

        initButtonsListeners(stopwatch)
    }

    private fun initButtonsListeners(stopwatch: Stopwatch) {
        binding.startPauceButton.setOnClickListener {
            if (stopwatch.isStarted) {
                listener.stop(stopwatch.id, stopwatch.currentMs)
            } else {
                listener.start(stopwatch.id)
            }
        }

        binding.restartButton.setOnClickListener { listener.reset(stopwatch.id) }

        binding.deleteButton.setOnClickListener { listener.delete(stopwatch.id) }
    }

    private fun startTimer(stopwatch: Stopwatch){
        val drawable = resources.getDrawable(R.drawable.ic_baseline_pause_24)
        binding.startPauceButton.setImageDrawable(drawable)
        binding.root.setBackgroundColor(resources.getColor(R.color.white))


        timer?.cancel()
        timer = getCountDownTimer(stopwatch)
        timer?.start()

        binding.blinkingIndicator.isInvisible = false
        (binding.blinkingIndicator.background as? AnimationDrawable)?.start()
    }

    private fun stopTimer(stopwatch: Stopwatch) {
        val drawable = resources.getDrawable(R.drawable.ic_baseline_play_arrow_24)
        binding.startPauceButton.setImageDrawable(drawable)

        binding.stopwatchTimer.text = stopwatch.currentMs.displayTime()
        binding.root.setBackgroundColor(resources.getColor(R.color.design_default_color_error))
        binding.timeLeftView.setCurrent(stopwatch.allTime)

        timer?.cancel()

        binding.blinkingIndicator.isInvisible = true
        (binding.blinkingIndicator.background as? AnimationDrawable)?.stop()
    }

    private fun getCountDownTimer(stopwatch: Stopwatch): CountDownTimer {
        return object : CountDownTimer(PERIOD, UNIT_TEN_MS) {
            val interval = UNIT_TEN_MS

            override fun onTick(millisUntilFinished: Long) {
                stopwatch.currentMs -= interval
                binding.stopwatchTimer.text = stopwatch.currentMs.displayTime()
                binding.timeLeftView.setCurrent(stopwatch.allTime - stopwatch.currentMs)


                if(stopwatch.currentMs <= 0)
                {
                    stopTimer(stopwatch)
                }
            }

            override fun onFinish() {
                 }
        }
    }

    private fun Long.displayTime(): String {
        if(this <= 0L){
            return START_TIME
        }
        val h = this / 1000 / 3600
        val m = this / 1000 % 3600 / 60
        val s = this / 1000 % 60
        val ms = this % 1000 / 10

        return "${displaySlot(h)}:${displaySlot(m)}:${displaySlot(s)}:${displaySlot(ms)}"
    }

    private fun displaySlot(count: Long): String{
        return if (count / 10L > 0)
        {
            "$count"
        }
        else{
            "0$count"
        }
    }

    private companion object {

        private const val START_TIME = "00:00:00:00"
        private const val UNIT_TEN_MS = 10L
        private const val PERIOD  = 1000L * 60L * 60L * 24L // Day
    }
}
