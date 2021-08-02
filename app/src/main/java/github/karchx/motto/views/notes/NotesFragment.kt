package github.karchx.motto.views.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import github.karchx.motto.databinding.FragmentNotesBinding
import github.karchx.motto.viewmodels.notes.SavedNotesViewModel
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.tools.managers.Arrow


class NotesFragment : Fragment() {

    private lateinit var notesViewModel: SavedNotesViewModel
    private lateinit var notesBottomSheet: BottomSheetBehavior<FrameLayout>

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
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
        notesViewModel = ViewModelProvider(this).get(SavedNotesViewModel::class.java)
    }

    private fun initViews() {
        notesBottomSheet = BottomSheetBehavior.from(binding.notesBottomSheet).apply {
            peekHeight = 100
            this.state = BottomSheetBehavior.STATE_COLLAPSED
            isHideable = true
        }
    }
}
