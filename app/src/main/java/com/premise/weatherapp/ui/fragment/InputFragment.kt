package com.premise.weatherapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders


import com.premise.weatherapp.R
import com.premise.weatherapp.databinding.FragmentInputBinding
import com.premise.weatherapp.ui.InputLocationViewModel


/**
 * Input Fragment is a thin container for the initial app screen which accepts
 */
class InputFragment : Fragment() {

    private lateinit var viewModel: InputLocationViewModel

    private lateinit var binding: FragmentInputBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(InputLocationViewModel::class.java)
        binding.inputViewModel = viewModel

    }
}