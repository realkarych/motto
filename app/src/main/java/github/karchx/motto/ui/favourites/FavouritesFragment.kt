package github.karchx.motto.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import github.karchx.motto.R
import github.karchx.motto.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment() {

    private lateinit var favouritesViewModel: FavouritesViewModel
    private var _binding: FragmentFavouritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favouritesViewModel =
            ViewModelProvider(this).get(FavouritesViewModel::class.java)

        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textFavourites
        favouritesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
