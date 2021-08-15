package github.karchx.motto.ads

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import github.karchx.motto.models.user_settings.UserPrefs
import github.karchx.motto.views.MainActivity


class AdViewer(private val activity: MainActivity, private val context: Context) {

    private val userPrefs = UserPrefs(activity)

    fun displayBanner(adView: AdView?) {
        val adRequest: AdRequest = AdRequest.Builder().build()
        adView?.loadAd(adRequest)
    }

    fun displayFullNoteAd() {
        val numberOfDisplayedAds = userPrefs.noteOpenings.getNumberOfOpens()

        if (numberOfDisplayedAds < 2) {
            userPrefs.noteOpenings.updateNumberOfOpens()
        } else {
            userPrefs.noteOpenings.resetNumberOfOpens()
            try {
                _displayFullNoteAd()
            } catch (ex: Exception) {
            }

        }
    }

    fun displayFullMottoAd() {
        val numberOfDisplayedAds = userPrefs.mottoOpenings.getNumberOfOpens()

        if (numberOfDisplayedAds < 4) {
            userPrefs.mottoOpenings.updateNumberOfOpens()
        } else {
            userPrefs.mottoOpenings.resetNumberOfOpens()
            try {
                _displayFullMottoAd()
            } catch (ex: Exception) {
            }

        }
    }

    private fun _displayFullNoteAd() {
        var mInterstitialAd: InterstitialAd?

        MobileAds.initialize(context) { }
        val adRequest: AdRequest = AdRequest.Builder().build()

        InterstitialAd.load(activity, AdIds.fullNoteId, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd

                    if (mInterstitialAd != null) {
                        mInterstitialAd!!.show(activity)
                    } else {
                        mInterstitialAd = null
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                }
            })
    }

    private fun _displayFullMottoAd() {
        var mInterstitialAd: InterstitialAd?

        MobileAds.initialize(context) { }
        val adRequest: AdRequest = AdRequest.Builder().build()

        InterstitialAd.load(activity, AdIds.fullMottoId, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd

                    if (mInterstitialAd != null) {
                        mInterstitialAd!!.show(activity)
                    } else {
                        mInterstitialAd = null
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                }
            })
    }
}
