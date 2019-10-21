package com.premise.weatherapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.premise.weatherapp.R
import com.premise.weatherapp.model.WeatherDay
import androidx.databinding.ViewDataBinding

import androidx.databinding.DataBindingUtil
import com.premise.weatherapp.BR

class ForcastAdapter(var items: List<WeatherDay>?, val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.forcast_item_layout, parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(items?.get(position)!!)


    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items?.size ?: 0
    }
}

class ViewHolder(dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root) {
    var binding: ViewDataBinding? = null
    init{
        binding = dataBinding
    }

    fun bind(data:WeatherDay){
        this.binding?.setVariable(BR.weatherDay, data)
    }
}