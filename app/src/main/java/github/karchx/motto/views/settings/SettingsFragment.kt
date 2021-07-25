package github.karchx.motto.views.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import github.karchx.motto.databinding.FragmentSettingsBinding
import github.karchx.motto.models.user_settings.UserPrefs
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.tools.managers.Arrow

class SettingsFragment: Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Arrow.hideBackArrow(activity as MainActivity)
        initData()
        initViews()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initData() {

    }

    private fun initViews() {

    }
}