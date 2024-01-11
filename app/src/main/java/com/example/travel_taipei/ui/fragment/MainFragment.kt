package com.example.travel_taipei.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.example.travel_taipei.R
import com.example.travel_taipei.adapter.MainTabAdapter
import com.example.travel_taipei.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import timber.log.Timber

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private lateinit var tabAdapter: MainTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        Timber.d("onCreateView")

        initTab()

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }

    private fun initTab() {
        tabAdapter = MainTabAdapter(requireContext(), parentFragmentManager, lifecycle)
        binding!!.vpMain.adapter = tabAdapter

        TabLayoutMediator(binding!!.tlMain, binding!!.vpMain) { tab, position ->
            tab.text = resources.getStringArray(R.array.main_tabTitle)[position]
        }.attach()
    }
}