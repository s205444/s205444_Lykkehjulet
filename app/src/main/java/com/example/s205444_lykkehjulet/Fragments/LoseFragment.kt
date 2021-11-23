package com.example.s205444_lykkehjulet.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.s205444_lykkehjulet.R
import com.example.s205444_lykkehjulet.ViewHolders.SharedViewModel

class LoseFragment : Fragment() {

    private lateinit var newGameButton: Button
    private lateinit var wordToShow: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lose_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        wordToShow = view.findViewById(R.id.scoreTextView)
        newGameButton = view.findViewById(R.id.startNewGameButton)

        newGameButton.setOnClickListener {
            findNavController().navigate(R.id.GameFragment)
        }
        wordToShow.text = viewModel.wordTextView().value.toString()
        viewModel.wordTextView().observe(viewLifecycleOwner,
            Observer {
            wordToShow.text = it.toString()
            newGameButton.text = it.toString()})


    }
}