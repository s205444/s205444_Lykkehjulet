package com.example.s205444_lykkehjulet.ViewHolders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.s205444_lykkehjulet.Model.GameManager
import com.example.s205444_lykkehjulet.Model.GameState

class SharedViewModel : ViewModel() {
    private val _lives = MutableLiveData<Int>()
    private val _points = MutableLiveData<Int>()
    private val _lettersUsed = MutableLiveData<String>()
    private val _spinMessage = MutableLiveData<String>()
    private val _wordTextView = MutableLiveData<String>()
    private val _isGameLost = MutableLiveData<Boolean>()
    private val _isGameWon = MutableLiveData<Boolean>()
    private val _isWheelSpun = MutableLiveData<Boolean>()
    private val _wordToGuess = MutableLiveData<String>()
    private val _isGamePaused = MutableLiveData<Boolean>()

    private val gameManager = GameManager()

    fun isGamePaused() : LiveData<Boolean>{
        return _isGamePaused
    }

    fun wordToGuess() : LiveData<String>{
        return _wordToGuess
    }

    fun isWheelSpun() : LiveData<Boolean>{
        return _isWheelSpun
    }

    fun lives() : LiveData<Int>{
        return _lives
    }

    fun points() : LiveData<Int>{
        return _points
    }

    fun lettersUsed() : LiveData<String>{
        return _lettersUsed
    }

    fun spinMessage() : LiveData<String>{
        return _spinMessage
    }

    fun wordTextView() : LiveData<String>{
        return _wordTextView
    }

    fun isGameWon() : LiveData<Boolean>{
        return _isGameWon
    }

    fun isGameLost() : LiveData<Boolean>{
        return _isGameLost
    }

    fun spinWheel(){
        val gameState = gameManager.spinWheel()
        updateData(gameState)
    }

    fun startNewGame() {
        val gameState = gameManager.startNewGame()
        updateData(gameState)
    }

    fun setIsWheelSpun(){
        val gameState = gameManager.setIsWheelSpun()
        updateData(gameState)
    }

    fun chooseLetter(letter : Char){
        val gameState = gameManager.play(letter)
        updateData(gameState)
    }

    fun pauseGame(){
        _isGamePaused.value = true
    }

    private fun updateData(gameState: GameState) {
        when (gameState) {
            is GameState.Lost -> {
                _wordToGuess.value = gameState.wordToGuess
                _isGameLost.value = true
                _lettersUsed.value = gameState.lettersUsed
                _lives.value = gameState.lives
            }
            is GameState.Running -> {
                _wordTextView.value = gameState.underscoreWord
                _lettersUsed.value = gameState.lettersUsed
                _lives.value = gameState.lives
                _points.value = gameState.points
                _spinMessage.value = gameState.fortuneResult
                _isWheelSpun.value = gameState.isWheelSpun
                _isGameLost.value = false
                _isGameWon.value = false
                _isGamePaused.value = false
            }
            is GameState.Won -> {
                _wordToGuess.value = gameState.wordToGuess
                _isGameWon.value = true
                _lettersUsed.value = gameState.lettersUsed
                _lives.value = gameState.lives
            }
        }
    }

}