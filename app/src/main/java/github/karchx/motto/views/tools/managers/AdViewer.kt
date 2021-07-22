package github.karchx.motto.views.tools.managers

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.views.MainActivity


class AdViewer {
    companion object {

        fun displayUnderDashboardAd(adView: AdView?) {
            val adRequest: AdRequest = AdRequest.Builder().build()
            adView?.loadAd(adRequest)
        }

        fun displayFullMottoAd(activity: MainActivity, context: Context) {
            val sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE)
            val numberOfDisplayedAds = sharedPrefs.getInt(Constants.NUMBER_OF_DISPLAYED_ADS, 0)

            if (numberOfDisplayedAds < 3) {
                sharedPrefs.edit()
                    .putInt(Constants.NUMBER_OF_DISPLAYED_ADS, numberOfDisplayedAds + 1).apply()
            } else {
                sharedPrefs.edit().putInt(Constants.NUMBER_OF_DISPLAYED_ADS, 0).apply()
                _displayFullMottoAd(activity, context)
            }
        }

        private fun _displayFullMottoAd(activity: MainActivity, context: Context) {
            var mInterstitialAd: InterstitialAd?

            MobileAds.initialize(context) { }
            val adRequest: AdRequest = AdRequest.Builder().build()

            InterstitialAd.load(activity, "ca-app-pub-1234211612737764/7941586817", adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.i("tag", "onAdLoaded")
                        mInterstitialAd = interstitialAd

                        if (mInterstitialAd != null) {
                            mInterstitialAd!!.show(activity)
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.")
                            mInterstitialAd = null
                        }
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Log.i("tag", loadAdError.message)
                    }
                })
        }
    }
}
