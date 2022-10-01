package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.FragmentGameResultBinding

class FragmentGameResult : Fragment() {

    private var _binding : FragmentGameResultBinding? = null
    private val binding : FragmentGameResultBinding
    get() = _binding ?: throw RuntimeException("FragmentGameResultBinding == null")

    private val args by navArgs<FragmentGameResultArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAgainListeners()
        setResultData()
    }

    private fun setAgainListeners() {
        binding.buttonTryAgain.setOnClickListener {
            tryAgain()
        }
    }

    private fun setResultData() {
        binding.gameResult = args.result
    }

    private fun tryAgain() {
        findNavController().popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}