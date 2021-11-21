package com.example.s205444_lykkehjulet

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.navigation.findNavController
import org.w3c.dom.Text
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
    private lateinit var pointsTextView : TextView
    private lateinit var spinResultsTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        lifeView = findViewById(R.id.livesTextView)
        wordTextView = findViewById(R.id.wordTextView)
        lettersUsedTextView = findViewById(R.id.lettersUsedTextView)

        newGameButton = findViewById(R.id.newGameButton)
        lettersLayout = findViewById(R.id.lettersLayout)

        pointsTextView = findViewById(R.id.pointsTextView)
        spinResultsTextView = findViewById(R.id.spinResultsTextView)
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
                    var gameState = gameManager.getGameState()
                    when(gameState){
                        is GameState.Running ->{
                            if(gameState.isWheelSpun) {
                                letterView.visibility = View.GONE
                                gameState = gameManager.play((letterView).text[0])
                                updateUI(gameState)
                                gameState = gameManager.setIsWheelSpun()
                            }
                        }
                    }

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
                lifeView.text = "Lives: ${gameState.lives}"
                pointsTextView.text = "Points: ${gameState.points}"
                spinResultsTextView.text = "You spun: ${gameState.fortuneResult}"

            }
            is GameState.Won -> showGameWon(gameState.wordToGuess)
        }
    }

    private fun spinWheel(){
        val gameState = gameManager.spinWheel()
        updateUI(gameState)
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
