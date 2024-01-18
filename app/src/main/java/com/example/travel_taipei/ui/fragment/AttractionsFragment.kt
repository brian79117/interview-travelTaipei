package com.example.travel_taipei.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travel_taipei.MainApplication.Companion.appResources
import com.example.travel_taipei.R
import com.example.travel_taipei.adapter.AttractionsAdapter
import com.example.travel_taipei.databinding.FragmentAttractionsBinding
import com.example.travel_taipei.util.LIST_PAGE_SIZE
import com.example.travel_taipei.viewmodel.AttractionsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AttractionsFragment : Fragment() {

    private var binding: FragmentAttractionsBinding? = null
    private val attractionsVM: AttractionsViewModel by viewModels()
    private val attractionsAdapter = AttractionsAdapter()
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

        initAttractionsList()
        initObservers()

        attractionsVM.getAttractionsList(false)

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

    private fun initObservers() {
        attractionsVM.isLoading.observe(viewLifecycleOwner) {
            Timber.d("isLoading = $it")
            if (it) {
                attractionsAdapter.setLoadingItem()
            } else {
                attractionsAdapter.removeLoadingItem()
            }
        }

        attractionsVM.attractionsListData.observe(viewLifecycleOwner) {
            attractionsAdapter.setData(it)
        }

        attractionsVM.errorMsg.observe(viewLifecycleOwner) {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(it)
                .setPositiveButton(appResources.getString(R.string.confirm)) { dialog, _ ->
                    dialog.dismiss()
                }
        }
    }

    private fun initAttractionsList() {
        binding!!.rvAttractions.adapter = attractionsAdapter
        binding!!.rvAttractions.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding!!.rvAttractions.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager as LinearLayoutManager

                if (manager.findLastVisibleItemPosition() == (manager.itemCount - 1) &&
                    !attractionsVM.isLoading.value!! &&
                    LIST_PAGE_SIZE * attractionsVM.page < attractionsVM.totalCount
                ) {
                    attractionsVM.getAttractionsList(true)
                }
            }
        })

        attractionsAdapter.setItemClickable(true)
    }
}