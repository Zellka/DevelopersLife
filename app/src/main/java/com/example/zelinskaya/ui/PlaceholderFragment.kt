package com.example.zelinskaya.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.zelinskaya.R
import com.example.zelinskaya.model.Meme
import com.example.zelinskaya.databinding.FragmentMainBinding
import com.example.zelinskaya.utils.Constants

class PlaceholderFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var memeViewModel: MemeViewModel
    private var memeList: MutableList<Meme> = ArrayList()
    private var memeIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = ViewModelFactory(arguments?.getInt(Constants.SECTION_NUMBER) ?: 1)
        memeViewModel = ViewModelProvider(this, viewModelFactory).get(MemeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        binding.swipeRefresh.setOnRefreshListener {
            binding.progressBar.visibility = View.INVISIBLE
            showMeme()
            binding.swipeRefresh.isRefreshing = false
        }
        showMeme()

        binding.btnPrev.isEnabled = false
        binding.btnPrev.setOnClickListener {
            if (memeIndex > 0) {
                memeIndex--
                replaceFragment(memeList, memeIndex)
                if (memeIndex == 0) {
                    binding.btnPrev.isEnabled = false
                }
                if (memeIndex == memeList.size - 2) {
                    binding.btnNext.isEnabled = true
                }
            }
        }
        binding.btnNext.setOnClickListener {
            if (memeIndex < memeList.size - 1) {
                memeIndex++
                replaceFragment(memeList, memeIndex)
                if (memeIndex == memeList.size - 1) {
                    binding.btnNext.isEnabled = false
                }
                if (memeIndex == 1) {
                    binding.btnPrev.isEnabled = true
                }
            } else {
                showMessage(R.string.try_later, R.drawable.ic_error)
            }
        }
    }

    private fun showMeme() {
        if (isNetworkConnected()) {
            memeViewModel.getData()
            memeViewModel.memesMutableLiveData.observe(
                viewLifecycleOwner,
                { postModels ->
                    memeList = postModels
                    if (memeList.size > 0) {
                        replaceFragment(memeList, memeIndex)
                    }
                }
            )
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            showMessage(R.string.no_internet_connection, R.drawable.ic_internet_error)
        }
    }

    private fun showMessage(message: Int, image: Int) {
        val layout: View = this.layoutInflater.inflate(R.layout.layout_error, null)
        val text = layout.findViewById<View>(R.id.text_error) as TextView
        val img = layout.findViewById<View>(R.id.img_error) as ImageView
        text.text = context?.resources?.getString(message)
        text.width = 900
        img.setImageResource(image)
        Toast(context).apply {
            duration = Toast.LENGTH_LONG
            view = layout
            setGravity(Gravity.TOP, 0, 0)
        }.show()
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            this.context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting
    }

    private fun replaceFragment(jokes: MutableList<Meme>, moviesIndex: Int) {
        childFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, MemeFragment.newInstance(jokes[moviesIndex]))
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constants.SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}