package com.example.s205444_lykkehjulet.Fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.s205444_lykkehjulet.Model.GameManager
import com.example.s205444_lykkehjulet.Model.GameState
import com.example.s205444_lykkehjulet.GameViewModel
import com.example.s205444_lykkehjulet.R
import com.example.s205444_lykkehjulet.ViewHolders.SharedViewModel

class GameFragment : Fragment() {


    private lateinit var viewModel: GameViewModel

    private val gameManager = GameManager()


    private lateinit var wordTextView: TextView
    private lateinit var lettersUsedTextView: TextView
    private lateinit var lifeView: TextView
    private lateinit var newGameButton: Button
    private lateinit var lettersLayout: ConstraintLayout
    private lateinit var spinWheelButton: Button
    private lateinit var pointsTextView : TextView
    private lateinit var spinResultsTextView : TextView

    private var isWheelSpun : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        lifeView = view.findViewById(R.id.livesTextView)
        wordTextView =  view.findViewById(R.id.wordTextView)
        lettersUsedTextView = view.findViewById(R.id.lettersUsedTextView)
        newGameButton = view.findViewById(R.id.newGameButton)
        lettersLayout = view.findViewById(R.id.lettersLayout)
        pointsTextView = view.findViewById(R.id.pointsTextView)
        spinResultsTextView = view.findViewById(R.id.spinResultsTextView)
        newGameButton.setOnClickListener {
            viewModel.startNewGame()
        }
        spinWheelButton = view.findViewById(R.id.spinWheelButton)

        viewModel.startNewGame()

        //lifeView.text = "Lives: ${gameState.lives}"
        viewModel.lives().observe(viewLifecycleOwner,
            Observer { lifeView.text = "Lives:${it.toString()}" })

        //wordTextView.text = gameState.underscoreWord
        viewModel.wordTextView().observe(viewLifecycleOwner,
            Observer { wordTextView.text = it.toString() })

        //lettersUsedTextView.text = "Letters used: ${gameState.lettersUsed}"
        viewModel.lettersUsed().observe(viewLifecycleOwner,
            Observer { lettersUsedTextView.text = "Letters used: ${it.toString()}" })

        //pointsTextView.text = "Points: ${gameState.points}"
        viewModel.points().observe(viewLifecycleOwner,
            Observer { pointsTextView.text = "Points: ${it.toString()}" })

        //spinResultsTextView.text = "You spun: ${gameState.fortuneResult}"
        viewModel.spinMessage().observe(viewLifecycleOwner,
            Observer { spinResultsTextView.text = "You spun: ${it.toString()}" })


        spinWheelButton.setOnClickListener{viewModel.spinWheel()}

        viewModel.isWheelSpun().observe(viewLifecycleOwner,
            Observer { isWheelSpun = it })

        lettersLayout.children.forEach { letterView ->
            if (letterView is TextView) {
                letterView.setOnClickListener {
                            if(this.isWheelSpun) {
                                viewModel.chooseLetter(letterView.text[0])
                                letterView.visibility = View.GONE
                                //gameState = gameManager.play((letterView).text[0])
                                //updateUI(gameState)
                                //gameState = gameManager.setIsWheelSpun()
                                viewModel.setIsWheelSpun()
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
                //lifeView.text = "Lives: ${gameState.lives}"
                pointsTextView.text = "Points: ${gameState.points}"
                spinResultsTextView.text = "You spun: ${gameState.fortuneResult}"

            }
            is GameState.Won -> showGameWon(gameState.wordToGuess)
        }
    }

    private fun spinWheel(){
    findNavController().navigate(R.id.WinFragment)
    //val gameState = gameManager.spinWheel()
        //updateUI(gameState)
    }

    private fun showGameLost(wordToGuess: String) {

        wordTextView.text = wordToGuess
        //gameLostTextView.visibility = View.VISIBLE
        //imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.game7))
        ////lettersLayout.visibility = View.GONE
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
        //lettersLayout.visibility = View.VISIBLE
       // lettersLayout.children.forEach { letterView ->
       //     letterView.visibility = View.VISIBLE
       // }
       // lifeView.visibility = View.VISIBLE
        updateUI(gameState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        // TODO: Use the ViewModel
    }

}