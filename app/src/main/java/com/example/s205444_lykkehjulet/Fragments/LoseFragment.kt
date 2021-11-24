package com.example.s205444_lykkehjulet.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.s205444_lykkehjulet.R
import com.example.s205444_lykkehjulet.ViewHolders.SharedViewModel
import com.example.s205444_lykkehjulet.databinding.LoseFragmentBinding

class LoseFragment : Fragment() {

    private var _binding: LoseFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoseFragmentBinding.inflate(inflater, container, false)

        viewModel.wordToGuess().observe(viewLifecycleOwner, Observer {
            binding.scoreTextView.text = "${getString(R.string.Word_to_guess)} ${it.toString()}"
        })

        binding.startNewGameButton.setOnClickListener {
            findNavController().navigate(R.id.action_LoseFragment_to_GameFragment)
        }

        return binding.root
    }
}