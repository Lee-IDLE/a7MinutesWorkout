package com.example.a7minutesworkout

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.window.OnBackInvokedCallback
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            binding?.toolbarFinish?.title = "완료"
        }

        binding?.toolbarFinish?.setNavigationOnClickListener{
            finish()

        }
        /*
            // 뒤로가기 이벤트 가져오기
            val callback = object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {

                }
            }
        */

        binding?.btnFinish?.setOnClickListener{
            finish()
        }
        
        // Dao 객체 생성
        val historyDao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(historyDao)
    }

    private fun addDateToDatabase(historyDao: HistoryDao){
        // 날짜 객체 생성
        val c = Calendar.getInstance()
        // 시간 객체 생성
        val dateTime = c.time
        Log.e("Date: ", dateTime.toString())
        
        // 날짜 포맷 설정
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        // 시간 객체를 날짜 포맷에 맞춰 변경
        val date = sdf.format(dateTime)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date = date))
        }
    }
}