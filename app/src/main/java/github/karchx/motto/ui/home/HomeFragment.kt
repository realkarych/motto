package github.karchx.motto.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import github.karchx.motto.R
import github.karchx.motto.databinding.FragmentHomeBinding
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.ui.adapters.MottosRecyclerAdapter
import github.karchx.motto.ui.listeners.OnClickRecyclerItemListener
import github.karchx.motto.ui.managers.Copier
import github.karchx.motto.ui.managers.DialogViewer
import github.karchx.motto.ui.managers.Toaster


class HomeFragment : Fragment() {

    private lateinit var mottos: ArrayList<Motto>
    private lateinit var clickedMotto: Motto

    private lateinit var mRandomMottosRecycler: RecyclerView
    private lateinit var mSwipeRefreshLayoutRandomMottos: SwipeRefreshLayout
    private lateinit var mFullMottoDialog: Dialog
    private lateinit var mFullMottoCardView: CardView

    private lateinit var homeViewModel: HomeViewModel

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

        observeRandomMottos()
        setFullMottoCardViewClickListener()
        setRandomMottosClickListener()

        mSwipeRefreshLayoutRandomMottos.setOnRefreshListener {
            homeViewModel.putRandomMottosPostValue()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeRandomMottos() {
        homeViewModel.randomMottos.observe(viewLifecycleOwner, {
            mottos = it
            displayMottosRecycler(mottos)

            if (mSwipeRefreshLayoutRandomMottos.isRefreshing) {
                mSwipeRefreshLayoutRandomMottos.isRefreshing = false
            }
        })
    }

    private fun setFullMottoCardViewClickListener() {
        mFullMottoCardView.setOnClickListener {
            val text = Copier.getMottoDataToCopy(
                context = requireContext(),
                quote = clickedMotto.quote,
                source = clickedMotto.source
            )

            Copier.copyText(requireActivity(), text)
            Toaster.displayTextIsCopiedToast(requireContext())
        }
    }

    private fun setRandomMottosClickListener() {
        mRandomMottosRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedMotto = mottos[position]
                    DialogViewer.displayFullMottoDialog(mFullMottoDialog, clickedMotto)
                }
            })
        )
    }

    private fun displayMottosRecycler(mottos: ArrayList<Motto>) {
        val layoutManager = GridLayoutManager(context, 1)
        val adapter = MottosRecyclerAdapter(mottos)

        mRandomMottosRecycler.setHasFixedSize(true)
        mRandomMottosRecycler.layoutManager = layoutManager
        mRandomMottosRecycler.adapter = adapter
    }

    private fun initViews() {
        mRandomMottosRecycler = binding.recyclerviewRandomMottos
        mSwipeRefreshLayoutRandomMottos = binding.refreshContainerOfRecyclerviewRandomMottos

        mFullMottoDialog = Dialog(requireActivity())
        mFullMottoDialog.setContentView(R.layout.dialog_full_motto)
        mFullMottoCardView = mFullMottoDialog.findViewById(R.id.cardview_full_motto_item)
    }
}
