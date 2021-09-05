package com.example.zelinskaya.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.zelinskaya.model.Meme
import com.example.zelinskaya.databinding.ItemMemeBinding
import com.example.zelinskaya.utils.Constants

class MemeFragment : Fragment() {

    private var _binding: ItemMemeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemMemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textDescription.text = arguments?.getString(Constants.MEME_DESCRIPTION)
        Glide
            .with(this)
            .load(arguments?.getString(Constants.MEME_URL))
            .into(binding.imageMeme)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(meme: Meme): MemeFragment {
            val args = Bundle()
            args.putString(Constants.MEME_DESCRIPTION, meme.description)
            args.putString(Constants.MEME_URL, meme.gifURL)

            val fragment = MemeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}