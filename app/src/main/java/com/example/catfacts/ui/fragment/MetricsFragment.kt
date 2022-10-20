package com.example.catfacts.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.catfacts.R
import com.example.catfacts.ui.MainActivity
import com.example.catfacts.viewmodel.CatBreedsViewModel
import com.example.catfacts.viewmodel.CatFactViewModel
import kotlinx.android.synthetic.main.fragment_metrics.*

class MetricsFragment : Fragment(R.layout.fragment_metrics) {
    lateinit var catBreedsViewModel: CatBreedsViewModel
    lateinit var catFactViewModel: CatFactViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Called viewmodels to observe average api call time
        catBreedsViewModel = (activity as MainActivity).catBreedsViewModel
        catFactViewModel = (activity as MainActivity).catFactViewModel

        //Observe average response time from cat breeds api call
        catBreedsViewModel.averageResponseTime.observe(viewLifecycleOwner) { responseTime ->
            //Build.MODEL -> Return phones model
            //Build.RELEASE -> Return phones release model

            tv_deviceName.text = "Device Name : " + Build.MODEL
            tv_osType.text = "Os Type : " + Build.VERSION.RELEASE
            tv_averageTime.text = "Average Time : " + responseTime
        }
        //Observe average response time from cat facts api call
        catFactViewModel.averageResponseTime.observe(viewLifecycleOwner) { responseTime ->
            tv_averageForFacts.text = "Average Time : " + responseTime
        }

    }

}
