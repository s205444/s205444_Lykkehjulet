package com.example.s205444_lykkehjulet.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.s205444_lykkehjulet.R
import com.example.s205444_lykkehjulet.ViewHolders.SharedViewModel
import com.example.s205444_lykkehjulet.databinding.WinFragmentBinding

class WinFragment : Fragment() {

    private var _binding: WinFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WinFragmentBinding.inflate(inflater, container, false)

        viewModel.points().observe(viewLifecycleOwner, Observer {
            binding.scoreTextView.text = "${getString(R.string.number_of_points)} ${it.toString()}"
        })

        return binding.root
    }


}