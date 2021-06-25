package github.karchx.motto.views.tools.adapters

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.views.dashboard.DashboardFragment

class DashboardLayoutAdapter(fragment: Fragment, private val context: Context) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = DashboardFragment()
        fragment.arguments = Bundle().apply {
            putString(
                Constants.KEYWORD_MOTTO_TYPE,
                Constants.getMottoTypesNames(context)[position]
            )
        }
        return fragment
    }
}
