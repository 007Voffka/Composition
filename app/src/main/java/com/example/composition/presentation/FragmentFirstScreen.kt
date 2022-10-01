package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentFirstScreenBinding

class FragmentFirstScreen : Fragment() {

    private var _binding : FragmentFirstScreenBinding? = null
    private val binding : FragmentFirstScreenBinding
    get() = _binding ?: throw RuntimeException("FragmentFirstScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonStartChoosingMode.setOnClickListener {
            launchChooseLevelFragment()
        }
    }

    private fun launchChooseLevelFragment() {
        findNavController().navigate(R.id.action_fragmentFirstScreen_to_fragmentChooseLevel)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}