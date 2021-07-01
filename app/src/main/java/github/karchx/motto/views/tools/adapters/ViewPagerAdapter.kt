package github.karchx.motto.views.tools.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.dashboard.AuthorsFragment
import github.karchx.motto.views.dashboard.TopicsFragment

class ViewPagerAdapter(activity: MainActivity) : FragmentStateAdapter(activity) {

    private val fragments = arrayListOf(AuthorsFragment(), TopicsFragment())

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}
