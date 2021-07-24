package by.psa.karpiks.promodoro

data class Stopwatch (
    val id: Int,
    var currentMs: Long,
    var allTime: Long,
    var isStarted: Boolean
)

interface StopwatchListener {
    fun start(id: Int)

    fun stop(id: Int, currentMs: Long)

    fun reset(id: Int)

    fun delete(id: Int)
}