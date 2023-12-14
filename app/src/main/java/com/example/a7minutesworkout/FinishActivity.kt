package com.example.a7minutesworkout

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.window.OnBackInvokedCallback
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import com.example.a7minutesworkout.databinding.ActivityFinishBinding

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
    }
}