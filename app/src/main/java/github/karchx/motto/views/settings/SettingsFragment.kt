package github.karchx.motto.views.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import github.karchx.motto.databinding.FragmentSettingsBinding
import github.karchx.motto.models.user_settings.UserPrefs
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.tools.managers.Arrow

class SettingsFragment : Fragment() {

    // Data
    private lateinit var userPrefs: UserPrefs

    // Views
    private lateinit var textviewIsCopyWithSource: TextView
    private lateinit var textviewIsSortSourcesByRandom: TextView
    private lateinit var textviewIsSortMottosByRandom: TextView
    private lateinit var switchIsCopyWithSource: SwitchCompat
    private lateinit var switchIsSortSourcesByRandom: SwitchCompat
    private lateinit var switchIsSortMottosByRandom: SwitchCompat

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

        setSwitchesStates()
        handleSwitches()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setSwitchesStates() {
        switchIsCopyWithSource.isChecked = userPrefs.copySettings.isWithSource()
        switchIsSortSourcesByRandom.isChecked = userPrefs.sourcesRandomness.isRandom()
        switchIsSortMottosByRandom.isChecked = userPrefs.mottosRandomness.isRandom()
    }

    private fun handleSwitches() {
        switchIsCopyWithSource.setOnCheckedChangeListener { _, _ ->
            userPrefs.copySettings.updateState()
        }

        switchIsSortSourcesByRandom.setOnCheckedChangeListener { _, _ ->
            userPrefs.sourcesRandomness.updateState()
        }

        switchIsSortMottosByRandom.setOnCheckedChangeListener { _, _ ->
            userPrefs.mottosRandomness.updateState()
        }
    }

    private fun initData() {
        userPrefs = UserPrefs(activity as MainActivity)
    }

    private fun initViews() {
        textviewIsCopyWithSource = binding.textviewIsCopyWithSource
        textviewIsSortSourcesByRandom = binding.textviewIsSortSourcesByRandom
        textviewIsSortMottosByRandom = binding.textviewIsSortMottosByRandom
        switchIsCopyWithSource = binding.switchIsCopyWithSource
        switchIsSortSourcesByRandom = binding.switchIsSortSourcesByRandom
        switchIsSortMottosByRandom = binding.switchIsSortMottosByRandom
    }
}