package github.karchx.motto.viewmodels.dashboard.films

import android.app.Application
import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import github.karchx.motto.models.user_settings.UserPrefs


class FilmsFactory(application: Application, private val userPrefs: UserPrefs) :
    ViewModelProvider.Factory {
    private val mApplication = application
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FilmsDashboardViewModel(mApplication, userPrefs) as T
    }
}