package github.karchx.motto.views.tools.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.dashboard.AuthorsDashboardFragment
import github.karchx.motto.views.dashboard.FilmsDashboardFragment
import github.karchx.motto.views.dashboard.TopicsDashboardFragment

class ViewPagerAdapter(activity: MainActivity) : FragmentStateAdapter(activity) {

    private val fragments =
        arrayListOf(AuthorsDashboardFragment(), TopicsDashboardFragment(), FilmsDashboardFragment())

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}
