package com.example.s205444_lykkehjulet.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.s205444_lykkehjulet.Adapter.ItemAdapter
import com.example.s205444_lykkehjulet.R
import com.example.s205444_lykkehjulet.ViewHolders.SharedViewModel
import com.example.s205444_lykkehjulet.data.Datasource
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

        val myDataset = Datasource().loadCategories()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = ItemAdapter(this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        return binding.root
    }
}