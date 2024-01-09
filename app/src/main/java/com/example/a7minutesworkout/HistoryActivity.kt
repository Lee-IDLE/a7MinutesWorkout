package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import com.example.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(supportActionBar != null){
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            binding.toolbarHistory.title = "HISTORY"
        }

        binding.toolbarHistory.setNavigationOnClickListener{
            finish()
        }

        val historyDao = (application as WorkOutApp).db.historyDao()
        getAllCompletedDates(historyDao)
    }

    private fun getAllCompletedDates(historyDao: HistoryDao){
        Log.e("Date: ", "getAllCompletedDates run")
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect{ allCompletedDatesList ->
                for(i in allCompletedDatesList){
                    Log.e("Date: ", i.date.toString())
                }
            }
        }
    }
}