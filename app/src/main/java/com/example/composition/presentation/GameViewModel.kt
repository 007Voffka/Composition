package com.example.composition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.composition.R
import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import com.example.composition.domain.usecases.GenerateQuestionUseCase
import com.example.composition.domain.usecases.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private val context = application

    private var timer : CountDownTimer? = null

    private val repository = GameRepositoryImpl
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime : LiveData<String>
    get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question : LiveData<Question>
    get() = _question

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers : LiveData<Int>
    get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers : LiveData<String>
        get() = _progressAnswers

    private val _isEnoughCorrectAnswers = MutableLiveData<Boolean>()
    val isEnoughCorrectAnswers : LiveData<Boolean>
        get() = _isEnoughCorrectAnswers

    private val _isEnoughPercentOfCorrectAnswers = MutableLiveData<Boolean>()
    val isEnoughPercentOfCorrectAnswers : LiveData<Boolean>
        get() = _isEnoughPercentOfCorrectAnswers

    private val _minPercentValue = MutableLiveData<Int>()
    val minPercentValue : LiveData<Int>
        get() = _minPercentValue

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult : LiveData<GameResult>
        get() = _gameResult

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    fun startGame(level: Level) {
        setLevelAndSettings(level)
        startTimer()
        generateQuestion()
    }

    fun chooseAnswer(number : Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        checkIsEnoughAnswers()
    }

    private fun checkIsEnoughAnswers() {
        val percent = calculatePercentOfRightAnswers()
        _isEnoughPercentOfCorrectAnswers.value = percent >= gameSettings.minPercentOfRightAnswers
        if(countOfRightAnswers >= gameSettings.minCountOfRightAnswers) {
            _isEnoughCorrectAnswers.value = true
        }
    }

    private fun calculatePercentOfRightAnswers() : Int {
        return (countOfRightAnswers.toDouble()/countOfQuestions * 100).toInt()
    }

    private fun checkAnswer(number : Int) {
        val rightAnswer = _question.value?.rightAnswer
        if(number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun setLevelAndSettings(level : Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercentValue.value = gameSettings.minPercentOfRightAnswers
    }

    private fun generateQuestion() {
       _question.value = generateQuestionUseCase.invoke(gameSettings.maxSumValue)
    }

    private fun finishGame() {
        val percent = calculatePercentOfRightAnswers()
        val isWinner = percent >= gameSettings.minPercentOfRightAnswers &&
            countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _gameResult.value = GameResult(isWinner, countOfRightAnswers, countOfQuestions, gameSettings)
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millis: Long) {
                _formattedTime.value = formatTime(millis)
            }
            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun formatTime(millis: Long) : String {
        val seconds = millis / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}