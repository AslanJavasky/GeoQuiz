package com.seniorjavasky.geoquiz.presentation

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.seniorjavasky.geoquiz.R

private const val EXTRA_ANSWER_IS_TRUE = "com.seniorjavasky.geoquiz.answer_is_true"
private const val EXTRA_ANSWER_SHOWN = "com.seniorjavasky.geoquiz.answer_shown"

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        ObjectAnimator.ofFloat(
            showAnswerButton, View.ROTATION, 0f, 720f
        ).apply {

            duration = 4000
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }


        showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)


            //animate()
//            it.animate().apply {
//                duration=1000
//                rotation(360f)//вращение
////                translationX(-300f)//сдвиг
////                translationY(100f)
//                scaleX(2f)
//                scaleY(2f)
////                alpha(0.3f)
////                interpolator= AccelerateInterpolator()
//            }
        }

        with(window) {
            enterTransition = Slide(Gravity.LEFT)
            exitTransition = Explode()
        }

    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(RESULT_OK, data)

    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)

            }
        }
    }
}