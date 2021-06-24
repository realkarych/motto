package github.karchx.motto.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.databinding.FragmentFavouritesBinding
import github.karchx.motto.model.db.Motto
import github.karchx.motto.ui.tools.adapters.AuthorsRecyclerAdapter
import github.karchx.motto.ui.tools.adapters.FromDbMottosRecyclerAdapter
import github.karchx.motto.viewmodels.FavouritesViewModel
import github.karchx.motto.viewmodels.MottosViewModel

class FavouritesFragment : Fragment() {

    private lateinit var favouritesViewModel: FavouritesViewModel
    private lateinit var mottosViewModel: MottosViewModel
    
    private lateinit var mFavouriteMottosRecycler: RecyclerView

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initData()
        mottosViewModel.allMottos.observe(viewLifecycleOwner) {
            val layoutManager = GridLayoutManager(context, 1)
            val adapter = FromDbMottosRecyclerAdapter(it.reversed() as ArrayList<Motto>)
            mFavouriteMottosRecycler.setHasFixedSize(true)
            mFavouriteMottosRecycler.layoutManager = layoutManager
            mFavouriteMottosRecycler.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initData() {
        favouritesViewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)

        mottosViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(MottosViewModel(application = requireActivity().application)::class.java)
    }
    
    private fun initViews() {
        mFavouriteMottosRecycler = binding.recyclerviewFavouriteMottos
    }
}
