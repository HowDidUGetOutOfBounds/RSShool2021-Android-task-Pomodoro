package by.psa.karpiks.promodoro

import android.media.MediaCodec
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import by.psa.karpiks.promodoro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), StopwatchListener {

    private lateinit var binding: ActivityMainBinding

    private val stopwatchAdapter = StopwatchAdapter()
    private val stopwatches = mutableListOf<Stopwatch>()
    private var nextId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stopwatchAdapter
        }

        binding.addNewStopwatchButton.setOnClickListener {
            stopwatches.add(Stopwatch(nextId++, 0, false))
            stopwatchAdapter.submitList(stopwatches.toList())
        }
    }

    override fun start(id: Int) {
    }

    override fun stop(id: Int) {
        TODO("Not yet implemented")
    }

    override fun reset(id: Int) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    private fun changeStopwatch(id: Int, currentMs: Long?, isStarted: Boolean){
        val newTimes = mutableListOf<Stopwatch>()
    }
}