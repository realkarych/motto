package github.karchx.motto.ui.tools.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import github.karchx.motto.model.storages.Constants
import github.karchx.motto.ui.dashboard.DashboardFragment

class DashboardLayoutAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = DashboardFragment()
        fragment.arguments = Bundle().apply {
            putString(Constants.KEYWORD_MOTTO_TYPE, Constants.MOTTO_TYPES_NAMES[position])
        }
        return fragment
    }
}
