package github.karchx.motto.viewmodels.dashboard.authors

import android.app.Application
import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import github.karchx.motto.models.user_settings.UserPrefs


class AuthorsFactory(application: Application, private val userPrefs: UserPrefs) :
    ViewModelProvider.Factory {
    private val mApplication = application
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthorsDashboardViewModel(mApplication, userPrefs) as T
    }
}