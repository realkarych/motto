package github.karchx.motto.views.dashboard

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.R
import github.karchx.motto.databinding.FragmentTvChannelsDashboardBinding
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.search_engine.citaty_info_website.data.TVChannel
import github.karchx.motto.viewmodels.MottosViewModel
import github.karchx.motto.viewmodels.TVChannelsDashboardViewModel
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.tools.adapters.ChannelsRecyclerAdapter
import github.karchx.motto.views.tools.adapters.MottosRecyclerAdapter
import github.karchx.motto.views.tools.listeners.OnClickAddToFavouritesListener
import github.karchx.motto.views.tools.listeners.OnClickRecyclerItemListener
import github.karchx.motto.views.tools.managers.*
import github.karchx.motto.models.db.Motto as dbMotto

class TVChannelsDashboardFragment : Fragment(R.layout.fragment_tv_channels_dashboard) {

    private var _binding: FragmentTvChannelsDashboardBinding? = null
    private val binding get() = _binding!!

    // ViewModels
    private lateinit var tvChannelsDashboardViewModel: TVChannelsDashboardViewModel
    private lateinit var mottosViewModel: MottosViewModel

    // Views
    private lateinit var tvChannelsRecycler: RecyclerView
    private lateinit var tvChannelMottosRecycler: RecyclerView
    private lateinit var mottosLoadingProgressBar: ProgressBar
    private lateinit var fullMottoCardView: CardView
    private lateinit var fullMottoDialog: Dialog
    private lateinit var addToFavouritesImageView: ImageView
    private lateinit var notFoundMottosTextView: TextView

    //Data
    private lateinit var tvChannels: ArrayList<TVChannel>
    private lateinit var clickedTVChannel: TVChannel
    private lateinit var allDbMottos: List<dbMotto>
    private lateinit var tvChannelMottos: ArrayList<Motto>
    private lateinit var clickedMotto: Motto

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tvChannelsDashboardViewModel =
            ViewModelProvider(this).get(TVChannelsDashboardViewModel::class.java)
        _binding = FragmentTvChannelsDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        initData()
        initViews()

        observeTVChannels()
        observeTVChannelMottos()
        observeDbMottos()

        handleTVChannelsRecyclerItemClick()
        handleTVChannelMottosRecyclerItemClick()

        handleCopyMottoData()
        setAddToFavouritesBtnClickListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            reloadFragment()
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeTVChannels() {
        tvChannelsDashboardViewModel.tvChannels.observe(viewLifecycleOwner, { _tvChannels ->
            tvChannels = _tvChannels
            displayTVChannelsRecycler(tvChannels)
        })
    }

    private fun observeTVChannelMottos() {
        tvChannelsDashboardViewModel.tvChannelMottos.observe(
            viewLifecycleOwner,
            { _tvChannelMottos ->
                tvChannelMottos = _tvChannelMottos
                displayTVChannelMottosRecycler(tvChannelMottos)
            })
    }

    private fun observeDbMottos() {
        mottosViewModel.allMottos.observe(viewLifecycleOwner) { allMottos ->
            allDbMottos = allMottos
        }
    }

    private fun displayTVChannelsRecycler(tvChannels: ArrayList<TVChannel>) {
        Arrow.hideBackArrow(activity as MainActivity)

        val layoutManager = GridLayoutManager(context, 2)
        val adapter = ChannelsRecyclerAdapter(this@TVChannelsDashboardFragment, tvChannels)

        tvChannelsRecycler.setHasFixedSize(true)
        tvChannelsRecycler.layoutManager = layoutManager
        tvChannelsRecycler.adapter = adapter
    }

    private fun displayTVChannelMottosRecycler(tvChannelMottos: ArrayList<Motto>) {
        Arrow.displayBackArrow(activity as MainActivity)
        if (tvChannelMottos.isEmpty()) {
            tvChannelsRecycler.visibility = View.GONE
            notFoundMottosTextView.visibility = View.VISIBLE
            mottosLoadingProgressBar.visibility = View.INVISIBLE
        } else {
            notFoundMottosTextView.visibility = View.INVISIBLE
            tvChannelsRecycler.visibility = View.GONE
            mottosLoadingProgressBar.visibility = View.INVISIBLE

            val layoutManager = GridLayoutManager(context, 1)
            val adapter = MottosRecyclerAdapter(tvChannelMottos)

            tvChannelMottosRecycler.scheduleLayoutAnimation()
            tvChannelMottosRecycler.setHasFixedSize(true)
            tvChannelMottosRecycler.layoutManager = layoutManager
            tvChannelMottosRecycler.adapter = adapter
        }
    }

    private fun setAddToFavouritesBtnClickListener() {
        addToFavouritesImageView.setOnClickListener {
            OnClickAddToFavouritesListener.handleMotto(
                requireContext(),
                mottosViewModel,
                addToFavouritesImageView,
                allDbMottos,
                clickedMotto
            )
        }
    }

    private fun handleTVChannelsRecyclerItemClick() {
        tvChannelsRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), tvChannelsRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedTVChannel = tvChannels[position]
                    tvChannelsDashboardViewModel.putTVChannelMottosPostValue(clickedTVChannel)
                    mottosLoadingProgressBar.visibility = View.VISIBLE
                }

                override fun onItemLongClick(view: View, position: Int) {
                    clickedTVChannel = tvChannels[position]
                    tvChannelsDashboardViewModel.putTVChannelMottosPostValue(clickedTVChannel)
                    mottosLoadingProgressBar.visibility = View.VISIBLE
                }
            })
        )
    }

    private fun handleTVChannelMottosRecyclerItemClick() {
        tvChannelMottosRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), tvChannelMottosRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedMotto = tvChannelMottos[position]
                    DialogViewer.displayFullMottoDialog(
                        requireContext(),
                        fullMottoDialog,
                        clickedMotto,
                        allDbMottos
                    )
                    observeDbMottos()
                }

                override fun onItemLongClick(view: View, position: Int) {
                    clickedMotto = tvChannelMottos[position]
                    DialogViewer.displayFullMottoDialog(
                        requireContext(),
                        fullMottoDialog,
                        clickedMotto,
                        allDbMottos
                    )
                    observeDbMottos()
                }
            })
        )
    }

    private fun handleCopyMottoData() {
        fullMottoCardView.setOnClickListener {
            val text = Copier.getMottoDataToCopy(
                context = requireContext(),
                quote = clickedMotto.quote,
                source = clickedMotto.source
            )

            Copier.copyText(requireActivity(), text)
            Toaster.displayTextIsCopiedToast(requireContext())
        }
    }

    private fun reloadFragment() {
        findNavController().navigate(
            R.id.navigation_dashboard,
            arguments,
            NavOptions.Builder()
                .setPopUpTo(R.id.navigation_dashboard, true)
                .build()
        )
    }

    private fun initData() {

    }

    private fun initViews() {
        tvChannelsRecycler = binding.recyclerviewTvChannelsDashboard
        tvChannelMottosRecycler = binding.recyclerviewTvChannelsMottos

        mottosLoadingProgressBar = binding.progressbarMottosLoading

        fullMottoDialog = Dialog(requireActivity())
        fullMottoDialog.setContentView(R.layout.dialog_full_motto)

        fullMottoCardView = fullMottoDialog.findViewById(R.id.cardview_full_motto_item)
        addToFavouritesImageView = fullMottoDialog.findViewById(R.id.imageview_is_saved_motto)

        notFoundMottosTextView = binding.textviewMottosNotFoundDashboard

        mottosViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(MottosViewModel(application = requireActivity().application)::class.java)
    }
}
