package com.ssverma.covidtracker.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ssverma.covidtracker.R
import com.ssverma.covidtracker.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAboutBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
        return binding.root
    }

}