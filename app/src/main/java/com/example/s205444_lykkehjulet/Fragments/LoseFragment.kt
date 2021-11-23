package com.example.s205444_lykkehjulet.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.s205444_lykkehjulet.R

class LoseFragment : Fragment() {

    private lateinit var newGameButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lose_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newGameButton = view.findViewById(R.id.startNewGameButton)

        newGameButton.setOnClickListener {
            findNavController().navigate(R.id.GameFragment)
        }
    }
}