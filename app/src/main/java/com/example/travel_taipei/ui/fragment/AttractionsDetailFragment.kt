package com.example.travel_taipei.ui.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.travel_taipei.MainApplication.Companion.appResources
import com.example.travel_taipei.R
import com.example.travel_taipei.adapter.BannerAdapter
import com.example.travel_taipei.adapter.IndicatorAdapter
import com.example.travel_taipei.databinding.FragmentAttractionsDetailBinding
import com.example.travel_taipei.viewmodel.AttractionsDetailViewModel
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AttractionsDetailFragment : Fragment() {

    private var binding: FragmentAttractionsDetailBinding? = null
    private val attractionsDetailVM: AttractionsDetailViewModel by viewModels()
    private val bannerAdapter = BannerAdapter()
    private val indicatorAdapter = IndicatorAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")

        initTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAttractionsDetailBinding.inflate(layoutInflater, container, false)

        Timber.d("onCreateView, ${attractionsDetailVM.params?.name}")
        initBanner()
        initView()

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

    private fun initTransition() {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
    }

    private fun initView() {
        requireActivity().title = attractionsDetailVM.params?.name

        binding!!.txvOpenTimeTitle.text =
            appResources.getString(R.string.attractionDetail_title_openTime)
        binding!!.txvAddressTitle.text =
            appResources.getString(R.string.attractionDetail_title_address)
        binding!!.txvPhoneTitle.text = appResources.getString(R.string.attractionDetail_title_phone)
        binding!!.txvUrlTitle.text = appResources.getString(R.string.attractionDetail_title_url)

        binding!!.txvOpenTime.text = attractionsDetailVM.params?.openTime
        binding!!.txvAddress.text = attractionsDetailVM.params?.address
        binding!!.txvPhone.text = attractionsDetailVM.params?.phone
        binding!!.txvUrl.text = attractionsDetailVM.params?.url
        binding!!.txvContent.text =
            Html.fromHtml(attractionsDetailVM.params?.content, Html.FROM_HTML_MODE_LEGACY)

        if (!attractionsDetailVM.params?.url.isNullOrBlank()) {
            binding!!.txvUrl.setOnClickListener {
                val action =
                    AttractionsDetailFragmentDirections.actionAttractionsDetailFragmentToWebViewFragment(
                        attractionsDetailVM.getWebViewParams()
                    )
                findNavController().navigate(action)
            }
        }

        if ((attractionsDetailVM.params?.images?.size ?: 0) == 0)
            binding!!.ivEmpty.visibility = View.VISIBLE else View.GONE
    }

    private fun initBanner() {
        bannerAdapter.setData(attractionsDetailVM.params?.images!!)
        indicatorAdapter.setLength(attractionsDetailVM.params?.images?.size ?: 0)

        binding!!.vpBanner.adapter = bannerAdapter

        binding!!.rvIndicator.adapter = indicatorAdapter
        binding!!.rvIndicator.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        binding!!.vpBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicatorAdapter.setCurrentPos(position)
            }
        })
    }
}