package com.example.travel_taipei.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.travel_taipei.MainApplication.Companion.appResources
import com.example.travel_taipei.R
import com.example.travel_taipei.adapter.MainTabAdapter
import com.example.travel_taipei.databinding.FragmentMainBinding
import com.example.travel_taipei.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private val mainVM: MainViewModel by viewModels()
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

        initBarMenu()
        initTab()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("onViewCreated")
    }
    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }

    private fun initTab() {
        tabAdapter = MainTabAdapter(childFragmentManager, lifecycle)
        binding!!.vpMain.adapter = tabAdapter

        TabLayoutMediator(binding!!.tlMain, binding!!.vpMain) { tab, position ->
            tab.text = appResources.getStringArray(R.array.main_tabTitle)[position]
        }.attach()
    }

    private fun initBarMenu() {
        requireActivity().title = appResources.getString(R.string.app_name)
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(appResources.getString(R.string.select_language))
                    .setNeutralButton(appResources.getString(R.string.cancel)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(appResources.getString(R.string.confirm)) { _, _ ->
                        mainVM.setLanguage()
                        initTab()
                        requireActivity().title = appResources.getString(R.string.app_name)
                    }
                    .setSingleChoiceItems(
                        appResources.getStringArray(R.array.language_options),
                        mainVM.getCheckedLanguage()
                    ) { _, which ->
                        mainVM.checkedItem = which
                    }
                    .show()

                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}