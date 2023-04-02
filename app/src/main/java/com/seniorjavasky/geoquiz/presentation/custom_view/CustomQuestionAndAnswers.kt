package com.seniorjavasky.geoquiz.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.StringRes
import com.seniorjavasky.geoquiz.databinding.CustomQuestionAndAnswersBinding

class CustomQuestionAndAnswers
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val binding = CustomQuestionAndAnswersBinding.inflate(
        LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun setQuestionText(@StringRes question:Int){
        binding.questionTextView.setText(question)
    }


}