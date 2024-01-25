package com.example.travel_taipei.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.travel_taipei.databinding.FragmentWebViewBinding
import com.example.travel_taipei.viewmodel.WebViewViewModel
import com.google.android.material.transition.MaterialSharedAxis
import timber.log.Timber

class WebViewFragment : Fragment() {

    private var binding: FragmentWebViewBinding? = null
    private val webViewVM: WebViewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")

        initTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWebViewBinding.inflate(layoutInflater, container, false)

        Timber.d("onCreateView")

        initWebView()
        requireActivity().title = webViewVM.params?.title
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }

    private fun initTransition() {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
    }

    private fun initWebView() {

        binding!!.wvWeb.webViewClient = object : WebViewClient() {
            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                if (binding != null) {
                    Timber.d("onLoadResource progress: ${binding?.wvWeb?.progress}")
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding?.lpiLoading?.visibility = View.GONE
                Timber.d("onPageFinished")
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)

                Timber.d("onReceivedError: ${error?.description}")
            }
        }

        binding!!.wvWeb.settings.javaScriptEnabled = true
        binding!!.wvWeb.settings.domStorageEnabled = true
        binding!!.wvWeb.settings.setSupportZoom(true)

        binding!!.wvWeb.loadUrl(webViewVM.params!!.url)
    }
}