package com.example.s205444_lykkehjulet.Model


sealed class GameState {
    class Running(
        val lettersUsed: String,
        val underscoreWord: String,
        val drawable: Int,
        val lives : Int,
        val points : Int,
        val isWheelSpun : Boolean,
        val fortuneResult : String,
        val potentialPoints : Int
        ) : GameState()

    class Lost(val wordToGuess: String) : GameState()
    class Won(val wordToGuess: String) : GameState()
}