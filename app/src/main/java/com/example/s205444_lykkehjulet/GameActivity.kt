package com.example.s205444_lykkehjulet

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import kotlin.random.Random


class GameActivity : AppCompatActivity() {

    private val gameManager = GameManager()

    private lateinit var wordTextView: TextView
    private lateinit var lettersUsedTextView: TextView
    private lateinit var lifeView: TextView
    //private lateinit var gameLostTextView: TextView
    //private lateinit var gameWonTextView: TextView
    private lateinit var newGameButton: Button
    private lateinit var lettersLayout: ConstraintLayout
    private lateinit var spinWheelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        lifeView = findViewById(R.id.livesTextView)
        wordTextView = findViewById(R.id.wordTextView)
        lettersUsedTextView = findViewById(R.id.lettersUsedTextView)
       // gameLostTextView = findViewById(R.id.gameLostTextView)
       // gameWonTextView = findViewById(R.id.gameWonTextView)
        newGameButton = findViewById(R.id.newGameButton)
        lettersLayout = findViewById(R.id.lettersLayout)
        newGameButton.setOnClickListener {
            startNewGame()
        }
        spinWheelButton = findViewById(R.id.spinWheelButton)
        spinWheelButton.setOnClickListener{spinWheel()}

        val gameState = gameManager.startNewGame()
        updateUI(gameState)

        lettersLayout.children.forEach { letterView ->
            if (letterView is TextView) {
                letterView.setOnClickListener {
                        val gameState = gameManager.play((letterView).text[0])
                        updateUI(gameState)
                        letterView.visibility = View.GONE
                }
            }
        }
    }

    private fun updateUI(gameState: GameState) {
        when (gameState) {
            is GameState.Lost -> showGameLost(gameState.wordToGuess)
            is GameState.Running -> {
                wordTextView.text = gameState.underscoreWord
                lettersUsedTextView.text = "Letters used: ${gameState.lettersUsed}"
                //imageView.setImageDrawable(ContextCompat.getDrawable(this, gameState.drawable))

                lifeView.text = switchEnums()

            }
            is GameState.Won -> showGameWon(gameState.wordToGuess)
        }
    }

    private fun spinWheel(){
        gameManager.spinWheel()
    }

    private fun switchEnums() : String {
        val randomInt = Random.nextInt(EnumFortunes.values().size)
        when(randomInt){
            1 -> return EnumFortunes.BANKRUPT.toString()
            2 -> return EnumFortunes.THOUSAND.toString()
            else -> return "failed switch"
        }

    }

    private fun showGameLost(wordToGuess: String) {
        wordTextView.text = wordToGuess
        //gameLostTextView.visibility = View.VISIBLE
        //imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.game7))
        lifeView.visibility = View.GONE
        lettersLayout.visibility = View.GONE
    }

    private fun showGameWon(wordToGuess: String) {
        wordTextView.text = wordToGuess
        //gameWonTextView.visibility = View.VISIBLE
        lettersLayout.visibility = View.GONE
    }

    private fun startNewGame() {
        //gameLostTextView.visibility = View.GONE
        //gameWonTextView.visibility = View.GONE
        val gameState = gameManager.startNewGame()
        lettersLayout.visibility = View.VISIBLE
        lettersLayout.children.forEach { letterView ->
            letterView.visibility = View.VISIBLE
        }
        lifeView.visibility = View.VISIBLE
        updateUI(gameState)
    }
}