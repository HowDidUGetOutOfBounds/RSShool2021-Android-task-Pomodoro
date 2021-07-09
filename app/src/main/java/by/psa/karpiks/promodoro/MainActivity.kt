package by.psa.karpiks.promodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.psa.karpiks.promodoro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}