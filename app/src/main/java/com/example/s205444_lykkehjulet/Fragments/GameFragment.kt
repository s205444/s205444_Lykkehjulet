package com.example.s205444_lykkehjulet.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.s205444_lykkehjulet.Adapter.ItemAdapter
import com.example.s205444_lykkehjulet.R
import com.example.s205444_lykkehjulet.ViewHolders.SharedViewModel
import com.example.s205444_lykkehjulet.data.Datasource

class GameFragment : Fragment() {

    private lateinit var wordTextView: TextView
    private lateinit var lettersUsedTextView: TextView
    private lateinit var lifeView: TextView
    private lateinit var newGameButton: Button
    private lateinit var lettersLayout: ConstraintLayout
    private lateinit var spinWheelButton: Button
    private lateinit var pointsTextView : TextView
    private lateinit var spinResultsTextView : TextView

    private var isWheelSpun : Boolean = false
    private var isGamePaused : Boolean = false

    private val viewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifeView            = view.findViewById(R.id.livesTextView)
        wordTextView        =  view.findViewById(R.id.wordTextView)
        lettersUsedTextView = view.findViewById(R.id.lettersUsedTextView)
        newGameButton       = view.findViewById(R.id.newGameButton)
        lettersLayout       = view.findViewById(R.id.lettersLayout)
        pointsTextView      = view.findViewById(R.id.pointsTextView)
        spinResultsTextView = view.findViewById(R.id.spinResultsTextView)
        spinWheelButton     = view.findViewById(R.id.spinWheelButton)

        spinWheelButton.visibility = View.GONE

        newGameButton.setOnClickListener {
            viewModel.startNewGame()
            spinWheelButton.visibility = View.VISIBLE
            lettersLayout.children.forEach { letterView ->
                if (letterView is TextView) {
                    letterView.visibility = View.VISIBLE
                }
            }
        }

        viewModel.lives().observe(viewLifecycleOwner,
            Observer { lifeView.text = "${getString(R.string.lives)} ${it.toString()}" })

        viewModel.wordTextView().observe(viewLifecycleOwner,
            Observer { wordTextView.text = it.toString() })

        viewModel.lettersUsed().observe(viewLifecycleOwner,
            Observer { lettersUsedTextView.text = "${getString(R.string.used_letters)} ${it.toString()}" })

        viewModel.points().observe(viewLifecycleOwner,
            Observer { pointsTextView.text = "${getString(R.string.number_of_points)} ${it.toString()}" })

        viewModel.spinMessage().observe(viewLifecycleOwner,
            Observer { spinResultsTextView.text = "${getString(R.string.you_spun_this)} ${it.toString()}" })

        viewModel.isWheelSpun().observe(viewLifecycleOwner,
            Observer { isWheelSpun = it })

        viewModel.isGamePaused().observe(viewLifecycleOwner,
            Observer { isGamePaused = it  })

        spinWheelButton.setOnClickListener{
            if(!isGamePaused && !isWheelSpun){
                viewModel.spinWheel()}
        }

        lettersLayout.children.forEach { letterView ->
            if (letterView is TextView) {
                letterView.setOnClickListener {
                            if(this.isWheelSpun && !isGamePaused) {
                                viewModel.chooseLetter(letterView.text[0])
                                letterView.visibility = View.GONE
                                viewModel.setIsWheelSpun()
                            }
                }
                if(isGamePaused)
                    letterView.visibility = View.GONE
            }
        }

        viewModel.isGameLost().observe(viewLifecycleOwner, Observer {
            if(it && !isGamePaused){
                viewModel.pauseGame()
                findNavController().navigate(R.id.action_GameFragment_to_LoseFragment)
            }
        })

        viewModel.isGameWon().observe(viewLifecycleOwner, Observer {
            if(it && !isGamePaused){
                viewModel.pauseGame()
                findNavController().navigate(R.id.action_game_to_winFragment)
            }
        })

        val myDataset = Datasource().loadCategories()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = ItemAdapter(this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)
    }
}