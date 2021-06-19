package github.karchx.motto.ui.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.R
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
                    displayFullMottoDialog(clickedMotto = mottos[position])
                }
            })
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayFullMottoDialog(clickedMotto: Motto) {
        val dialog = Dialog(requireActivity())

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_full_motto)
        dialog.setTitle(getString(R.string.motto_about))
        dialog.show()

        val tvFullMottoQuote = dialog.findViewById<TextView>(R.id.textview_motto_full_quote)
        val tvFullMottoSource = dialog.findViewById<TextView>(R.id.textview_motto_full_source)
        tvFullMottoQuote.text = clickedMotto.quote
        tvFullMottoSource.text = clickedMotto.source
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
