package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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

            // 안보이게 셋팅
            binding.tvHistory.visibility = View.GONE
            binding.rvHistory.visibility = View.GONE
            binding.tvNoDataAvailable.visibility = View.VISIBLE
            historyDao.fetchAllDates().collect{ allCompletedDatesList ->
                // 값이 있는 경우에만 여기가 실행되므로 여기서 VISIBLE 처리
                binding.tvHistory.visibility = View.VISIBLE
                binding.rvHistory.visibility = View.VISIBLE
                binding.tvNoDataAvailable.visibility = View.GONE

                binding.rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)

                val dates = ArrayList<String>()
                for(date in allCompletedDatesList){
                    dates.add(date.date)
                }
                binding.rvHistory.adapter = HistoryAdapter(dates)
            }
        }
    }

}