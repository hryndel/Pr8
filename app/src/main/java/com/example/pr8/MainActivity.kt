package com.example.pr8

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.core.graphics.drawable.toDrawable
import com.example.pr8.databinding.ActivityMainBinding
import org.w3c.dom.Text
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate

class MainActivity : AppCompatActivity() {
    lateinit var mainHandler : Handler
    private  lateinit var binding: ActivityMainBinding
    private var alltime: MutableList<Int> = mutableListOf()
    private var secondsLeft:Int = 0
    private val updateTextTask = object :Runnable{
        override fun run() {
            TimerTick()
            mainHandler.postDelayed(this,1000)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainHandler = Handler(Looper.getMainLooper())
        Enable(true)
    }
    fun Generate (view: View) {
        super.onResume()////
        mainHandler.post(updateTextTask)////
        mainHandler = Handler(Looper.getMainLooper())
        binding.txtOperator.text = RandomOperator().toString()
        binding.txtFirstOperand.text = RandomOperand().toString()
        binding.txtSecondOperand.text = RandomOperand().toString()
        binding.txtAnswer.text = RandomAnswer(1).toString()
        binding.linearLayoutAnswer.background = Color.WHITE.toDrawable()
        Enable(false)
    }
    @SuppressLint("SetTextI18n")
    fun Check (view: View?){
        var color = Color.RED
        when (view?.id){
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
        super.onPause()
        MaxMinAvgCheck()
        Enable(true)
    }
    fun RandomOperand(): Int = (10..99).random()
    fun RandomOperator(): Char = arrayOf('+', '-', '/', '*').random()
    fun TimerTick():Int = secondsLeft++
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
    fun MaxMinAvgCheck(){
        alltime.add(secondsLeft)
        binding.txtMax.text = alltime.max().toString()
        binding.txtMin.text = alltime.min().toString()
        binding.txtAvg.text = String.format("%.2f", alltime.average())
    }
}
