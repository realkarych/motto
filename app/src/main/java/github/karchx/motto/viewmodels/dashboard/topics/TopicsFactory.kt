package github.karchx.motto.viewmodels.dashboard.topics

import android.app.Application
import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import github.karchx.motto.models.user_settings.UserPrefs


class TopicsFactory(application: Application, private val userPrefs: UserPrefs) :
    ViewModelProvider.Factory {
    private val mApplication = application
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TopicsDashboardViewModel(mApplication, userPrefs) as T
    }
}