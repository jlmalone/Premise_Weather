package com.premise.weatherapp.ui.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.premise.weatherapp.R
import com.premise.weatherapp.databinding.FragmentForecastBinding
import com.premise.weatherapp.ui.ForcastAdapter
import com.premise.weatherapp.ui.ForecastViewModel
import com.premise.weatherapp.ui.InputLocationViewModel


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ForecastFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ForecastFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ForecastFragment : Fragment() {

    private lateinit var forecastViewModel: ForecastViewModel
    private lateinit var inputViewModel: InputLocationViewModel

    private lateinit var binding: FragmentForecastBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inputViewModel = ViewModelProviders.of(activity!!).get(InputLocationViewModel::class.java)
        forecastViewModel = ViewModelProviders.of(activity!!).get(ForecastViewModel::class.java)
        if(savedInstanceState == null) {
            forecastViewModel.recyclerAdapter = ForcastAdapter(ArrayList(),activity!!)
            forecastViewModel.activity = activity
            forecastViewModel.fetchForcastData()
        }
        binding.inputViewModel = inputViewModel
        binding.forecastViewModel = forecastViewModel
        forecastViewModel.weatherLocationId = inputViewModel.inputLocationForm?.locationResults?.woeid
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = forecastViewModel.recyclerAdapter

    }

    override fun onResume() {
        super.onResume()
        forecastViewModel.fetchForcastData()
    }

    private var listener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
}
