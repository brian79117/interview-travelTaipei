package com.example.travel_taipei.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travel_taipei.R
import com.example.travel_taipei.databinding.FragmentNewsBinding
import timber.log.Timber

class NewsFragment : Fragment() {

    private var binding: FragmentNewsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(layoutInflater, container, false)

        Timber.d("onCreateView")
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}