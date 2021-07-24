package by.psa.karpiks.promodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import by.psa.karpiks.promodoro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), StopwatchListener {

    private lateinit var binding: ActivityMainBinding

    private val stopwatchAdapter = StopwatchAdapter(this)
    private val stopwatches = mutableListOf<Stopwatch>()
    private var nextId = 0
    private var startTime: Long? = 60000L

    private var current = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.customViewOne.setPeriod(PERIOD)
//
//        GlobalScope.launch {
//            while (current < PERIOD * REPEAT) {
//                current += INTERVAL
//                binding.customViewOne.setCurrent(current)
//                delay(10)
//            }
//        }

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stopwatchAdapter
        }

        initInputTimeLogic()

        binding.addNewStopwatchButton.setOnClickListener {
            stopwatches.add(Stopwatch(nextId++, startTime!!, startTime!!, false))
            stopwatchAdapter.submitList(stopwatches.toList())
        }
    }

    override fun start(id: Int) {
        changeStopwatch(id, null, true)
    }

    override fun stop(id: Int, currentMs: Long) {
        changeStopwatch(id, currentMs, false)
    }

    override fun reset(id: Int) {
        changeStopwatch(id, 0L, false)
    }

    override fun delete(id: Int) {
        stopwatches.remove(stopwatches.find { it.id == id })
        stopwatchAdapter.submitList(stopwatches.toList())
    }

    private fun changeStopwatch(id: Int, currentMs: Long?, isStarted: Boolean){
        val newTimers = mutableListOf<Stopwatch>()
        stopwatches.forEach {
            if (it.id == id) {
                newTimers.add(Stopwatch(it.id, currentMs ?: it.currentMs, it.allTime, isStarted))
            } else {
                newTimers.add(it)
            }
        }
        stopwatchAdapter.submitList(newTimers)
        stopwatches.clear()
        stopwatches.addAll(newTimers)
    }

    private companion object {

        private const val INTERVAL = 100L
        private const val PERIOD = 1000L * 30 // 30 sec
        private const val REPEAT = 10 // 10 times
    }

    //Read start value for new timer

    fun initInputTimeLogic() {
        binding.etAddMinutes.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                startTime = s.toString().toLongOrNull()

                if (startTime != null) {
                    startTime = startTime!! * 1000
                } else
                {
                    startTime = 60000L
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        )
    }
}