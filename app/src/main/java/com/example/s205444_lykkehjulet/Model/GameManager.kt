package com.example.s205444_lykkehjulet.Model


import com.example.s205444_lykkehjulet.data.GameConstants
import kotlin.random.Random

/**
 * @author Lucas Loft Skals
 * Part of GameManager logic has inspiration from https://www.youtube.com/watch?v=kGGpH7ypxAU&t=82s&ab_channel=UsmaanDeveloper
 */

class GameManager {

    private var lettersUsed: String = ""
    private lateinit var underscoreWord: String
    private lateinit var wordToGuess: String
    private var lives = 5
    private var points: Int = 0
    private var isWheelSpun: Boolean = false
    private var fortuneText: String = ""
    private var potentialPoints: Int = 0

    fun startNewGame(): GameState {
        lettersUsed = ""
        points = 0
        lives = 5
        isWheelSpun = false
        fortuneText = ""
        potentialPoints = 0
        val randomIndex = Random.nextInt(0, GameConstants.words.size)
        wordToGuess = GameConstants.words[randomIndex]
        generateUnderscores(wordToGuess)
        return getGameState()
    }

    private fun generateUnderscores(word: String) {
        val sb = StringBuilder()
        word.forEach { char ->
            if (char == '/') {
                sb.append('/')
            } else {
                sb.append("_")
            }
        }
        underscoreWord = sb.toString()
    }

    fun spinWheel() : GameState {

        val randomInt = Random.nextInt(EnumFortunes.values().size)
        val fortune = EnumFortunes.values()[randomInt]

        when(fortune){
            EnumFortunes.BANKRUPT -> {
                points = 0
                isWheelSpun = false
                fortuneText = EnumFortunes.BANKRUPT.displayText
                return GameState.Running(
                    lettersUsed, underscoreWord, lives, points,
                    false, EnumFortunes.BANKRUPT.displayText, 0
                )
            }
            EnumFortunes.EXTRA_TURN -> {
                lives += 1
                isWheelSpun = false
                fortuneText = EnumFortunes.EXTRA_TURN.displayText
                return getGameState()
            }
            EnumFortunes.MISS_TURN -> {
                lives -= 1
                isWheelSpun = false
                fortuneText = EnumFortunes.MISS_TURN.displayText
                return getGameState()
            }
            EnumFortunes.HUNDRED -> {
                potentialPoints = EnumFortunes.HUNDRED.points
                isWheelSpun = true
                fortuneText = EnumFortunes.HUNDRED.displayText
                return GameState.Running(
                    lettersUsed, underscoreWord,  lives, points,
                    true, EnumFortunes.HUNDRED.displayText, potentialPoints
                )
            }
            EnumFortunes.THOUSAND -> {
                potentialPoints = EnumFortunes.THOUSAND.points
                isWheelSpun = true
                fortuneText = EnumFortunes.THOUSAND.displayText
                return GameState.Running(
                    lettersUsed, underscoreWord, lives, points,
                    true, EnumFortunes.THOUSAND.displayText, potentialPoints
                )
            }
        }


    }

    fun play(letter: Char): GameState {
        if (lettersUsed.contains(letter)) {
            points += potentialPoints
            return GameState.Running(
                lettersUsed,
                underscoreWord,
                lives,
                points,
                isWheelSpun,
                fortuneText,
                potentialPoints
            )
        }

        lettersUsed += letter
        val indexes = mutableListOf<Int>()

        wordToGuess.forEachIndexed { index, char ->
            if (char.equals(letter, true)) {
                indexes.add(index)
            }
        }

        var finalUnderscoreWord = "" + underscoreWord // _ _ _ _ _ _ _ -> E _ _ _ _ _ _
        indexes.forEach { index ->
            val sb = StringBuilder(finalUnderscoreWord).also {
                it.setCharAt(index, letter) }
            finalUnderscoreWord = sb.toString()
        }

        if (indexes.isEmpty()) {
            lives--
        }
        if(wordToGuess.contains(letter) || wordToGuess.contains(letter.lowercaseChar())){
        updatePoints()
        }
        underscoreWord = finalUnderscoreWord
        return getGameState()
    }

    private fun updatePoints(){
        points += potentialPoints
    }



    fun getGameState(): GameState {
        if (underscoreWord.equals(wordToGuess, true)) {
            return GameState.Won(wordToGuess, lives, lettersUsed, points)
        }

        if (lives == 0) {
            return GameState.Lost(wordToGuess, lives, lettersUsed)
        }


        return GameState.Running(
            lettersUsed,
            underscoreWord,
            lives,
            points,
            isWheelSpun,
            fortuneText,
            potentialPoints
        )
    }
    fun setIsWheelSpun(): GameState {
        isWheelSpun = false
        return getGameState()
    }
}