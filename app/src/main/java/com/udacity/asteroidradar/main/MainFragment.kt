package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.asteroid.AsteroidsAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.utils.AsteroidRadarFilter.ALL_TIME
import com.udacity.asteroidradar.utils.AsteroidRadarFilter.CURRENT_DAY
import com.udacity.asteroidradar.utils.AsteroidRadarFilter.WEEKLY

@RequiresApi(Build.VERSION_CODES.O)
class MainFragment : Fragment() {

  private val mainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

  private fun setupMainFragmentBinding(inflater: LayoutInflater): FragmentMainBinding {
    val mainFragmentBinding = FragmentMainBinding.inflate(inflater).apply {
      asteroidRecycler.adapter = AsteroidsAdapter().apply {
        mainViewModel.asteroids.observe(viewLifecycleOwner) { submitList(it) }
      }
      viewModel = mainViewModel
    }
    mainFragmentBinding.lifecycleOwner = this
    setHasOptionsMenu(true)
    return mainFragmentBinding
  }


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = setupMainFragmentBinding(inflater).root


  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.main_overflow_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    mainViewModel.settingFilter(
      itemSelected(item)
    )
    return true
  }

  private fun itemSelected(item: MenuItem) = when (item.itemId) {
    R.id.show_rent_menu -> CURRENT_DAY
    R.id.show_all_menu -> WEEKLY
    else -> ALL_TIME
  }
}
