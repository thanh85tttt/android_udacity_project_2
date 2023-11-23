package com.udacity.asteroidradar.asteroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidsAdapter : ListAdapter<Asteroid,
    AsteroidItemViewHolder>(AsteroidCallback()) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = from(parent)

  private fun from(parent: ViewGroup) =
    AsteroidItemViewHolder(
      ItemAsteroidBinding
        .inflate(
          LayoutInflater.from(parent.context), parent, false
        )
    )

  override fun onBindViewHolder(holder: AsteroidItemViewHolder, position: Int) =
    holder.bind(getItem(position))
}

class AsteroidCallback : DiffUtil.ItemCallback<Asteroid>() {
  override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid) = oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid) = oldItem == newItem
}

