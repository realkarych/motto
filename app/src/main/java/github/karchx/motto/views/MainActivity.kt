package github.karchx.motto.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import github.karchx.motto.R
import github.karchx.motto.databinding.ActivityMainBinding
import github.karchx.motto.views.tools.InAppUpdate


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var inAppUpdate: InAppUpdate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_notes,
                R.id.navigation_dashboard,
                R.id.navigation_favourites,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.selectedItemId = navView.menu.getItem(2).itemId

        window.navigationBarColor = ContextCompat.getColor(this, R.color.soft_black)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        try {
            MobileAds.initialize(this)
        } catch (ex: Exception) {
        }

        // Caused crashes on Xiaomi
        try {
            inAppUpdate = InAppUpdate(this)
        } catch (ex: Exception) {
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        inAppUpdate?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        inAppUpdate?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        inAppUpdate?.onDestroy()
    }
}
