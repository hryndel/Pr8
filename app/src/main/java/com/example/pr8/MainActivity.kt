package com.example.pr8

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import androidx.core.graphics.drawable.toDrawable
import com.example.pr8.databinding.ActivityMainBinding
import org.w3c.dom.Text
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private var alltime = listOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Enable(true)
    }
    fun Generate (view: View) {
        binding.txtOperator.text = RandomOperator().toString()
        binding.txtFirstOperand.text = RandomOperand().toString()
        binding.txtSecondOperand.text = RandomOperand().toString()
        binding.txtAnswer.text = RandomAnswer(1).toString()
        binding.linearLayoutAnswer.background = Color.WHITE.toDrawable()
        Enable(false)
        TimerTick() //start
    }
    @SuppressLint("SetTextI18n")
    fun Check (x: View?){
        var color = Color.RED
        when (x?.id){
            R.id.btnCorrect -> {
                if (binding.txtAnswer.text.toString() == RandomAnswer(0).toString()){
                    binding.txtCorrect.text = (binding.txtCorrect.text.toString().toInt() + 1).toString()
                    color = Color.GREEN
                }
                else binding.txtNotCorrect.text = (binding.txtNotCorrect.text.toString().toInt() + 1).toString()
            }
            R.id.btnNotCorrect -> {
                if (binding.txtAnswer.text.toString() != RandomAnswer(0).toString()){
                    binding.txtCorrect.text = (binding.txtCorrect.text.toString().toInt() + 1).toString()
                    color = Color.GREEN
                }
                else binding.txtNotCorrect.text = (binding.txtNotCorrect.text.toString().toInt() + 1).toString()
            }
        }
        binding.txtCount.text = (binding.txtCount.text.toString().toInt() + 1).toString()
        binding.txtPercent.text = String.format("%.2f%%", (binding.txtCorrect.text.toString().toDouble() / binding.txtCount.text.toString().toDouble()) * 100.0)
        binding.linearLayoutAnswer.background = color.toDrawable()
        Enable(true)
        TimerTick() // stop
    }
    fun RandomOperand(): Int = (10..99).random()
    fun RandomOperator(): Char = arrayOf('+', '-', '/', '*').random()
    fun RandomAnswer(x: Int): Any {
        return when (binding.txtOperator.text){
            "+" -> binding.txtFirstOperand.text.toString().toInt() + binding.txtSecondOperand.text.toString().toInt() + (0..x).random() * (-10..10).random()
            "-" -> binding.txtFirstOperand.text.toString().toInt() - binding.txtSecondOperand.text.toString().toInt() + (0..x).random() * (-10..10).random()
            "/" -> String.format("%.2f", binding.txtFirstOperand.text.toString().toDouble() / binding.txtSecondOperand.text.toString().toDouble() + (0..x).random() * (-2..2).random())
            "*" -> binding.txtFirstOperand.text.toString().toInt() * binding.txtSecondOperand.text.toString().toInt() + (0..x).random() * (-10..10).random()
            else -> "Error"
        }
    }
    fun Enable(value: Boolean){
        binding.btnStart.isEnabled = value
        binding.btnCorrect.isEnabled = !value
        binding.btnNotCorrect.isEnabled = !value
    }
    fun TimerTick(){
        var tick = 0
        alltime + tick
    }
    fun MaxMinAvgCheck(){
        if (alltime.last().toString() > binding.txtMax.text.toString()) binding.txtMax.text = alltime.last().toString()
        if (alltime.last().toString() < binding.txtMin.text.toString()) binding.txtMin.text = alltime.last().toString()
        binding.txtAvg.text = alltime.average().toString()
    }
}
