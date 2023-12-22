package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiBinding

    private var currentVisibleView: String = METRIC_UNITS_VIEW
    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

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

        makeVisibleMetricUnitsView()
        
        // 여기서 OnClickListener를 사용하면 어느 버튼이 선택됐는지 따로 확인해서
        // 처리해줘야 하므로 무엇이 선택됐는지 알려주는 setOnCheckedChangeListener가 더 좋다 
        binding.rgUnits.setOnCheckedChangeListener{ _, checkedId: Int ->
            if(checkedId == R.id.rbMetricUnits)
                makeVisibleMetricUnitsView()
            else
                makeVisibleUsMetricUnitsView()
        }
    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding.llMetricArea.visibility = View.VISIBLE
        binding.flUsUnitArea.visibility = View.GONE

        binding.etMetricUnitWeight.text!!.clear()
        binding.etUsMetricUnitHeightFeet.text!!.clear()
        binding.etUsMetricUnitHeightInch.text!!.clear()
    }

    private fun makeVisibleUsMetricUnitsView(){
        currentVisibleView = US_UNITS_VIEW
        binding.llMetricArea.visibility = View.GONE
        binding.flUsUnitArea.visibility = View.VISIBLE

        binding.etMetricUnitWeight.text!!.clear()
        binding.etMetricUnitHeight.text!!.clear()
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