package github.karchx.motto.views.tools.managers

import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import github.karchx.motto.views.MainActivity


class AdViewer {
    companion object {
        fun displayFullMottoAd(activity: MainActivity) {
            val adRequest = AdRequest.Builder().build()
            var mInterstitialAd: InterstitialAd?

            InterstitialAd.load(activity, "ca-app-pub-1234211612737764/7941586817", adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        mInterstitialAd = interstitialAd
                        Log.i("tag", "onAdLoaded")
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Log.i("tag", loadAdError.message)
                        mInterstitialAd = null
                    }
                })
        }
    }
}
