package com.example.composition.data

import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import com.example.composition.domain.repository.GameRepository
import kotlin.math.max
import kotlin.random.Random

object GameRepositoryImpl : GameRepository{

    private const val MIN_SUM_VALUE = 5
    private const val MIN_ANSWER_VALUE = 3

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val rightAnswer = sum - visibleNumber
        val options = HashSet<Int>()
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = max(maxSumValue, rightAnswer + countOfOptions)
        options.add(rightAnswer)
        while(options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level) {
            Level.TEST -> GameSettings(15, 2,
                50, 8)
            Level.EASY -> GameSettings(15, 10,
            65, 60)
            Level.MEDIUM -> GameSettings(25, 20,
            80, 50)
            Level.HARD -> GameSettings(35, 30,
            90, 40)
        }
    }
}