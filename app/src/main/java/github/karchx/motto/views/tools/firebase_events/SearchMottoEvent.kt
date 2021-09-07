package github.karchx.motto.views.tools.firebase_events

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import github.karchx.motto.views.MainActivity

class SearchMottoEvent : FirebaseEvent {

    private var firebaseAnalytics: FirebaseAnalytics? = null

    override fun pushEvent() {
        try {
            _pushEvent()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun _pushEvent() {
        firebaseAnalytics = MainActivity().firebaseAnalytics
        firebaseAnalytics?.logEvent(getCodeName(), getBundle())
    }

    private fun getCodeName(): String {
        return "motto_searched"
    }

    private fun getBundle(): Bundle {
        return Bundle()
    }
}