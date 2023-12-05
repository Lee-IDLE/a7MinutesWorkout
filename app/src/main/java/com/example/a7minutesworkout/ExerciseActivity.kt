package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExerciese)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            binding?.toolbarExerciese?.title = "타이머"
        }

        exerciseList = Constants.defaultExerciseList()

        binding?.toolbarExerciese?.setNavigationOnClickListener{
            finish()
        }
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        setupRestView()
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            finish()
        }
    }

    private fun setupRestView(){
        binding.flRestView?.visibility = View.VISIBLE
        binding.tvTitle?.visibility = View.VISIBLE
        binding.tvExerciseName?.visibility = View.INVISIBLE
        binding.flExerciseView?.visibility = View.INVISIBLE
        binding.ivImage?.visibility = View.INVISIBLE

        binding.tvUpcomingLabel?.visibility = View.VISIBLE
        binding.tvUpcomingExercise?.visibility = View.VISIBLE

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        binding.tvUpcomingExercise?.text = exerciseList!![currentExercisePosition + 1].getName()

        setRestProgressBar()
    }

    // 운동 진행 바
    private fun setupExerciseView(){
        binding.flRestView?.visibility = View.INVISIBLE
        binding.tvTitle?.visibility = View.INVISIBLE
        binding.tvExerciseName?.visibility = View.VISIBLE
        binding.flExerciseView?.visibility = View.VISIBLE
        binding.ivImage?.visibility = View.VISIBLE

        binding.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding.tvUpcomingExercise?.visibility = View.INVISIBLE

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()
        setExerciseProgressBar()
    }

    // 휴식 진행 바
    private fun setRestProgressBar(){
        binding.ProgressBar?.progress = restProgress

        restTimer = object : CountDownTimer(10 * 1000, 1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding.ProgressBar?.progress = 10 - restProgress
                binding.tvTimer?.text = (10 - restProgress).toString()


            }

            override fun onFinish() {
                currentExercisePosition++
                setupExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        binding.ProgressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(30 * 1000, 1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding.ProgressBarExercise?.progress = 30 - exerciseProgress
                binding.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! - 1){
                    setupRestView()
                }else{
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Congratulations! You have completed the 7 minutes workout.",
                        Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
    }
}