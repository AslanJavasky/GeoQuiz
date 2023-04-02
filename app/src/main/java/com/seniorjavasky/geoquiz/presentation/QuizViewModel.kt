package com.seniorjavasky.geoquiz.presentation

import androidx.lifecycle.ViewModel
import com.seniorjavasky.geoquiz.R
import com.seniorjavasky.geoquiz.data.Datasource

class QuizViewModel(): ViewModel() {
    val datasource= Datasource()
    private val questionBank=datasource.getList()
    var currentIndex=0
    var isCheater=false

    val currentQuestionAnswer:Boolean
        get()=questionBank[currentIndex].answer

    val currentQuestionText:Int
        get()=questionBank[currentIndex].textResId

    fun moveToNext(){
        currentIndex=(++currentIndex)%questionBank.size
    }
}