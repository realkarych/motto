package github.karchx.motto.views.tools.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.dashboard.items.*

class ViewPagerAdapter(activity: MainActivity) : FragmentStateAdapter(activity) {

    private val fragments = arrayListOf(
        BooksDashboardFragment(),
        FilmsDashboardFragment(),
        SeriesDashboardFragment(),
        AnimeDashboardFragment(),
        TopicsDashboardFragment(),
        AuthorsDashboardFragment(),
        TVChannelsDashboardFragment()
    )

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}
