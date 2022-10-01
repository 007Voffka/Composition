package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentChooseLevelBinding
import com.example.composition.domain.entity.Level

class FragmentChooseLevel : Fragment() {

    private var _binding : FragmentChooseLevelBinding? = null
    private val binding : FragmentChooseLevelBinding
    get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnButtonsClickListeners()
    }

    private fun setOnButtonsClickListeners() {
        with(binding) {
            buttonTestMode.setOnClickListener {
                launchGameFragment(Level.TEST)
            }
            buttonEasyMode.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            buttonMediumMode.setOnClickListener {
                launchGameFragment(Level.MEDIUM)
            }
            buttonHardMode.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }

    }

    private fun launchGameFragment(level : Level) {
        findNavController().navigate(FragmentChooseLevelDirections.actionFragmentChooseLevelToFragmentGame(level))
    }

    companion object {
        const val BACK_STACK_NAME = "chooseLevel"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}