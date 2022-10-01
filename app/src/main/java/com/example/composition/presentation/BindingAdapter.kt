package com.example.composition.presentation

import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult

interface OnOptionClickListener {
    fun onOptionClick(option : Int)
}

@BindingAdapter("countOfRequiredAnswers")
fun bindRequiredAnswers(textView : TextView, requiredCount : Int) {
    textView.text = String.format(
        textView.context.getString(R.string.countOfRightAnswers),
        requiredCount
    )
}
@BindingAdapter("countOfRightAnswers")
fun bindRightAnswers(textView : TextView, userCount : Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score),
        userCount
    )
}
@BindingAdapter("requiredPercent")
fun bindRequiredPercent(textView : TextView, percent : Int) {
    textView.text = String.format(
        textView.context.getString(R.string.necessary_percent),
        percent
    )
}
@BindingAdapter("rightAnswersPercent")
fun bindRightAnswersPercent(textView : TextView, gameResult : GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.right_answers_percent),
        ((gameResult.countOfRightAnswers.toDouble()/gameResult.countOfQuestions) * 100).toInt()
    )
}
@BindingAdapter("isWinner")
fun bindImageIsWinner(imageView : ImageView, isWinner : Boolean) {
    if(isWinner) {
        imageView.setImageDrawable(
            ContextCompat.getDrawable(imageView.context, R.drawable.happy))
    } else {
        imageView.setImageDrawable(
            ContextCompat.getDrawable(imageView.context, R.drawable.sad))
    }
}

@BindingAdapter("sum")
fun bindSumText(textView: TextView, sum : Int) {
    textView.text = sum.toString()
}

@BindingAdapter("visibleNumber")
fun visibleNumber(textView: TextView, num : Int) {
    textView.text = (num.toString())
}

@BindingAdapter("isEnoughCount")
fun bindEnoughCount(textView: TextView, enough : Boolean) {
    textView.setTextColor(getColorByState(textView, enough))
}

private fun getColorByState(textView: TextView, enough : Boolean) : Int {
    val colorResId = if(enough) {
        ContextCompat.getColor(textView.context, android.R.color.holo_green_dark)
    } else {
        ContextCompat.getColor(textView.context, android.R.color.holo_red_dark)
    }
    return colorResId
}

@BindingAdapter("isEnoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, enough: Boolean) {
    if(enough) {
        progressBar.progressTintList = ColorStateList.valueOf(ContextCompat.getColor
            (progressBar.context, android.R.color.holo_green_dark))
    } else {
        progressBar.progressTintList = ColorStateList.valueOf(ContextCompat.getColor
            (progressBar.context, android.R.color.holo_red_dark))
    }
}

@BindingAdapter("onOptionClick")
fun bindOnOptionClick(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}