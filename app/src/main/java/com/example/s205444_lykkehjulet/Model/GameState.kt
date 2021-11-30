package com.example.s205444_lykkehjulet.Model

/**
 * @author Lucas Loft Skals
 * Part of GameState logic has inspiration from https://www.youtube.com/watch?v=kGGpH7ypxAU&t=82s&ab_channel=UsmaanDeveloper
 */
sealed class GameState {
    class Running(
        val lettersUsed: String,
        val underscoreWord: String,
        val lives : Int,
        val points : Int,
        val isWheelSpun : Boolean,
        val fortuneResult : String,
        val potentialPoints : Int
        ) : GameState()

    class Lost(
        val wordToGuess: String,
        val lives: Int,
        val lettersUsed: String
        ) : GameState()

    class Won(val wordToGuess: String,
              val lives: Int,
              val lettersUsed: String) : GameState()
}