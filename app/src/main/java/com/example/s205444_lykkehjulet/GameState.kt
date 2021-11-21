package com.example.s205444_lykkehjulet


sealed class GameState {
    class Running(
        val lettersUsed: String,
        val underscoreWord: String,
        val drawable: Int,
        val lives : Int,
        val points : Int,
        val isWheelSpun : Boolean,
        ) : GameState()

    class Lost(val wordToGuess: String) : GameState()
    class Won(val wordToGuess: String) : GameState()
}