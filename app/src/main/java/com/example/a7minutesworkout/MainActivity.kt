package com.example.a7minutesworkout

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.a7minutesworkout.databinding.ActivityMainBinding


class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(window){
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            enterTransition = Slide(Gravity.END)
            enterTransition.duration = 700
            exitTransition = Slide(Gravity.START)
            exitTransition.duration = 700
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.flStart.setOnClickListener {
            val intent = Intent(this@MainActivity, ExerciseActivity::class.java)
            startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this@MainActivity).toBundle())
        }

        binding.flBMI.setOnClickListener{
            val intent = Intent(this@MainActivity, BMIActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        //binding = null
    }
}
