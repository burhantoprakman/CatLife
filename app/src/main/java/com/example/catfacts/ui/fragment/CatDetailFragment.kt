package com.example.catfacts.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.catfacts.R
import kotlinx.android.synthetic.main.fragment_cat_detail.*

class CatDetailFragment : Fragment(R.layout.fragment_cat_detail) {

    //Arguments which comes from Cat Breeds fragment with nav component
    private val args: CatDetailFragmentArgs? by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args?.let {
            tv_catBreed.text = "Breed : "+it.catDetail?.breed
            tv_catCountry.text = "Country : "+it.catDetail?.country
            tv_catOrigin.text = "Origin : "+ it.catDetail?.origin
            tv_catCoat.text = "Coat : "+it.catDetail?.coat
            tv_catPattern.text = "Pattern : "+it.catDetail?.pattern
        }
    }
}