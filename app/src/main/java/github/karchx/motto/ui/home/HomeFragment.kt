package github.karchx.motto.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.databinding.FragmentHomeBinding
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.ui.home.adapters.RandomMottosRecyclerAdapter
import github.karchx.motto.ui.listeners.OnClickMottoItemListener

class HomeFragment : Fragment() {

    // Data
    private lateinit var mottos: ArrayList<Motto>

    // Views
    private lateinit var mRandomMottosRecycler: RecyclerView

    // ViewModels
    private lateinit var homeViewModel: HomeViewModel

    //Bindings
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()

        homeViewModel.randomMottos.observe(viewLifecycleOwner, Observer {
            mottos = it
            displayMottosRecycler(mottos)
        })

        mRandomMottosRecycler.addOnItemTouchListener(
            OnClickMottoItemListener(requireContext(), object :
                OnClickMottoItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    val clickedMotto = mottos[position]
                    Log.d("posmotto", clickedMotto.toString())
                }
            })
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayMottosRecycler(mottos: ArrayList<Motto>) {
        val layoutManager = GridLayoutManager(context, 1)
        val adapter = RandomMottosRecyclerAdapter(mottos)

        mRandomMottosRecycler.setHasFixedSize(true)
        mRandomMottosRecycler.layoutManager = layoutManager
        mRandomMottosRecycler.adapter = adapter
    }

    private fun initViews() {
        mRandomMottosRecycler = binding.recyclerviewRandomMottos
    }
}
