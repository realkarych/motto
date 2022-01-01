package github.karchx.motto.viewmodels.dashboard.anime

import android.app.Application
import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import github.karchx.motto.models.user_settings.UserPrefs


class AnimeFactory(application: Application, private val userPrefs: UserPrefs) :
    ViewModelProvider.Factory {
    private val mApplication = application
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnimeDashboardViewModel(mApplication, userPrefs) as T
    }
}