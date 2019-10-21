package com.premise.weatherapp.ui.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.premise.weatherapp.R
import com.premise.weatherapp.ui.fragment.ForecastFragment

class MainActivity :AppCompatActivity(), ForecastFragment.OnFragmentInteractionListener
{
    //handles navigation events so we do not need to bother at the Fragment level
    override fun onFragmentInteraction(uri: Uri) {
        //unfortunate necessary override implementation needed to make NavComponent Function
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}