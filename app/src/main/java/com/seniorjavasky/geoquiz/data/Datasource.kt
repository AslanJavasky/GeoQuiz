package com.seniorjavasky.geoquiz.data

import com.seniorjavasky.geoquiz.R
import com.seniorjavasky.geoquiz.presentation.Question

class Datasource {

    fun getList() = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

}