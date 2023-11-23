package com.udacity.asteroidradar.asteroid

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding
import com.udacity.asteroidradar.main.MainFragmentDirections

class AsteroidItemViewHolder(private val itemBinding: ItemAsteroidBinding) :
  RecyclerView.ViewHolder(itemBinding.root) {

  fun bind(item: Asteroid) {
    with(itemBinding) {
      codenameTextview.text = item.codename
      dayTextview.text = item.closeApproachDate

      val iconResource =
        if (item.isPotentiallyHazardous) R.drawable.ic_status_potentially_hazardous
        else R.drawable.ic_status_normal

      iconImageview.setImageResource(iconResource)
      iconImageview.contentDescription =
        if (item.isPotentiallyHazardous) "Icon potentially hazardous"
        else "Icon status normal"

      itemElement.setOnClickListener {
        it.findNavController().navigate(MainFragmentDirections.actionShowDetail(item))
      }
    }
  }
}
