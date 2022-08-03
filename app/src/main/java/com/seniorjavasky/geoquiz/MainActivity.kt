package com.seniorjavasky.geoquiz


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

private const val TAG="MainActivity"
private const val KEY_INDEX="index"
private const val REQUEST_CODE_CHEAT=0
private const val EXTRA_ANSWER_SHOWN="com.seniorjavasky.geoquiz.answer_shown"




class MainActivity : AppCompatActivity() {

    private val quizViewModel:QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }


    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"OnCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex=savedInstanceState?.getInt(KEY_INDEX,0) ?: 0
        quizViewModel.currentIndex=currentIndex

        trueButton=findViewById(R.id.true_button)
        falseButton=findViewById(R.id.false_button)
        nextButton=findViewById(R.id.next_button)
        questionTextView=findViewById(R.id.question_text_view)
        cheatButton=findViewById(R.id.cheat_button)

        trueButton.setOnClickListener{view:View ->
            checkAnswer(true)

        }
        falseButton.setOnClickListener { view:View ->
            checkAnswer(false)
        }
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        cheatButton.setOnClickListener {
            //start CheatActivity
//            val intent= Intent(this, CheatActivity::class.java)
            val answerIsTrue=quizViewModel.currentQuestionAnswer
            val intent=CheatActivity.newIntent(
                this@MainActivity,
                answerIsTrue)
            //startActivity(intent)
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
        if (resultCode != Activity.RESULT_OK){
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN,false) ?: false
        }
    }



    private fun updateQuestion(){
        val questionTextResId=quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer:Boolean){
        val correctAnswer=quizViewModel.currentQuestionAnswer

        val messageResId=when{
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer ==correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId,Toast.LENGTH_SHORT).show()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG,"onSavedInstanceState")
        outState.putInt(KEY_INDEX,quizViewModel.currentIndex)
    }

//    override fun onStart() {
//        super.onStart()
//        Log.d(TAG,"onStart() called")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.d(TAG,"onResume() called")
//    }
//
//     override fun onPause() {
//        super.onPause()
//        Log.d(TAG,"onPause() called")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.d(TAG,"onStop() called")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d(TAG,"onDestroy() called")
//    }



}