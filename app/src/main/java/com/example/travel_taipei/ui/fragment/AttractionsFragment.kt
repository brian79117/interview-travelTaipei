package com.example.travel_taipei.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.travel_taipei.databinding.FragmentAttractionsBinding
import timber.log.Timber

class AttractionsFragment : Fragment() {

    private var binding: FragmentAttractionsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttractionsBinding.inflate(layoutInflater, container, false)

        Timber.d("onCreateView")
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}