package github.karchx.motto.views.dashboard

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.ads.AdView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import github.karchx.motto.databinding.FragmentDashboardLayoutBinding
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.models.storages.Constants.Companion.MOTTO_TYPES_ICONS
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.tools.adapters.ViewPagerAdapter
import github.karchx.motto.ads.AdViewer

class DashboardLayoutFragment : Fragment() {

    private var _binding: FragmentDashboardLayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var sharedPrefs: SharedPreferences
    private var mAdView: AdView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE)

        setViewPagerAndTabLayout()

        tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                sharedPrefs.edit().putInt(Constants.LAST_TAB_SELECTED, position).apply()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                sharedPrefs.edit().putInt(Constants.LAST_TAB_SELECTED, position).apply()
            }
        })

        mAdView = binding.adViewUnderDashboard
        AdViewer(activity as MainActivity, requireContext()).displayUnderDashboardAd(mAdView)
    }

    private fun setViewPagerAndTabLayout() {
        initViewPagerAndTabLayout()

        val adapter = ViewPagerAdapter(requireActivity() as MainActivity)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = Constants.getMottoTypesNames(requireContext())[position]
            tab.icon = ResourcesCompat.getDrawable(
                resources,
                MOTTO_TYPES_ICONS[position],
                requireContext().theme
            )
        }.attach()

        val currentItem = sharedPrefs.getInt(Constants.LAST_TAB_SELECTED, 0)

        val tab = tabLayout.getTabAt(currentItem)
        tab!!.select()

        val recyclerView = (viewPager).getChildAt(0)
        recyclerView.apply {
            val itemCount = adapter.itemCount
            if (itemCount >= currentItem) {
                viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                        viewPager.setCurrentItem(currentItem, false)
                    }
                })
            }
        }
    }

    private fun initViewPagerAndTabLayout() {
        viewPager = binding.pagerMottoTypes
        tabLayout = binding.tabLayoutMottoTypes
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
