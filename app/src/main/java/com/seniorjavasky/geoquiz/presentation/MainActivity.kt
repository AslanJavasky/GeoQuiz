package com.seniorjavasky.geoquiz.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.seniorjavasky.geoquiz.*
import com.seniorjavasky.geoquiz.databinding.ActivityMainBinding

private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0
private const val EXTRA_ANSWER_SHOWN = "com.seniorjavasky.geoquiz.answer_shown"
private lateinit var binding:ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        binding.customQuestionsAndAnswers.binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }
        binding.customQuestionsAndAnswers.binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }
        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(
                this@MainActivity,
                answerIsTrue
            )
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
        updateQuestion()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }


    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.customQuestionsAndAnswers.setQuestionText(questionTextResId)
        var currentIndex=quizViewModel.currentIndex
        binding.progressBar.progress=++currentIndex

    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

}