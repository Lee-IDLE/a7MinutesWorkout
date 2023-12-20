package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBMI)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.title = "CALCULATE BMI"
            //binding.toolbarBMI.title = "CALCULATE BMI"
        }

        binding.toolbarBMI.setNavigationOnClickListener{
            finish()
        }

        binding.btnCalculateUnits.setOnClickListener{
            if(validateMetricUnits()){
                // m 단위 이므로 100으로 나눔
                val heightValue: Float = binding.etMetricUnitHeight.text.toString().toFloat() / 100
                val weightValue : Float = binding.etMetricUnitWeight.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)
            }else{
                Toast.makeText(this@BMIActivity, "Please enter valid values.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String

        // compareTo는 같으면 0을, 작으면 음수 크면 양수를 결과 값으로 리턴한다.
        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat meal"
        }else if(bmi.compareTo(15f) > 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat meal"
        }else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        }else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout!"
        }else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0){
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout!"
        }else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0){
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout!"
        }else{
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.llDisplayBMIResult.visibility = View.VISIBLE
        binding.tvBMIValue.text = bmiValue
        binding.tvBMIType.text = bmiLabel
        binding.tvBMIDescription.text = bmiDescription
    }

    /// 입력값 유효성 확인
    private fun validateMetricUnits():Boolean{
        var isValid = true

        if(binding.etMetricUnitWeight.text.toString().isEmpty()){
            isValid = false
        }else if(binding.etMetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }
}