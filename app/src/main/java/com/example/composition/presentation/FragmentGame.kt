package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.databinding.FragmentGameBinding

class FragmentGame : Fragment() {

    private var _binding : FragmentGameBinding? = null
    private val binding : FragmentGameBinding
    get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")

    private lateinit var viewModel: GameViewModel
    private val args by navArgs<FragmentGameArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        viewModel.startGame(args.level)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.gameResult.observe(viewLifecycleOwner) {
            findNavController().navigate(FragmentGameDirections.actionFragmentGameToFragmentGameResult(it))
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}