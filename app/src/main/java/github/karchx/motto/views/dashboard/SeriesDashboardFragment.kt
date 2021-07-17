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
import github.karchx.motto.databinding.FragmentSeriesDashboardBinding
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.search_engine.citaty_info_website.data.TVSeries
import github.karchx.motto.viewmodels.MottosViewModel
import github.karchx.motto.viewmodels.TVSeriesDashboardViewModel
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.tools.adapters.MottosRecyclerAdapter
import github.karchx.motto.views.tools.adapters.SeriesRecyclerAdapter
import github.karchx.motto.views.tools.listeners.OnClickAddToFavouritesListener
import github.karchx.motto.views.tools.listeners.OnClickRecyclerItemListener
import github.karchx.motto.views.tools.managers.*
import github.karchx.motto.models.db.Motto as dbMotto

class SeriesDashboardFragment : Fragment(R.layout.fragment_tv_channels_dashboard) {

    private var _binding: FragmentSeriesDashboardBinding? = null
    private val binding get() = _binding!!

    // ViewModels
    private lateinit var seriesDashboardViewModel: TVSeriesDashboardViewModel
    private lateinit var mottosViewModel: MottosViewModel

    // Views
    private lateinit var seriesRecycler: RecyclerView
    private lateinit var seriesMottosRecycler: RecyclerView
    private lateinit var mottosLoadingProgressBar: ProgressBar
    private lateinit var fullMottoCardView: CardView
    private lateinit var fullMottoDialog: Dialog
    private lateinit var addToFavouritesImageView: ImageView
    private lateinit var notFoundMottosTextView: TextView

    //Data
    private lateinit var series: ArrayList<TVSeries>
    private lateinit var clickedSeries: TVSeries
    private lateinit var allDbMottos: List<dbMotto>
    private lateinit var seriesMottos: ArrayList<Motto>
    private lateinit var clickedMotto: Motto

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        seriesDashboardViewModel =
            ViewModelProvider(this).get(TVSeriesDashboardViewModel::class.java)
        _binding = FragmentSeriesDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        initData()
        initViews()

        observeSeries()
        observeSeriesMottos()
        observeDbMottos()

        handleSeriesRecyclerItemClick()
        handleSeriesMottosRecyclerItemClick()

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

    private fun observeSeries() {
        seriesDashboardViewModel.tvSeries.observe(viewLifecycleOwner, { _series ->
            series = _series
            displaySeriesRecycler(series)
        })
    }

    private fun observeSeriesMottos() {
        seriesDashboardViewModel.tvSeriesMottos.observe(viewLifecycleOwner, { _seriesMottos ->
            seriesMottos = _seriesMottos
            displaySeriesMottosRecycler(seriesMottos)
        })
    }

    private fun observeDbMottos() {
        mottosViewModel.allMottos.observe(viewLifecycleOwner) { allMottos ->
            allDbMottos = allMottos
        }
    }

    private fun displaySeriesRecycler(series: ArrayList<TVSeries>) {
        Arrow.hideBackArrow(activity as MainActivity)

        val layoutManager = GridLayoutManager(context, 2)
        val adapter = SeriesRecyclerAdapter(this@SeriesDashboardFragment, series)

        adapter.setHasStableIds(true)
        seriesRecycler.setHasFixedSize(true)
        seriesRecycler.layoutManager = layoutManager
        seriesRecycler.adapter = adapter
    }

    private fun displaySeriesMottosRecycler(seriesMottos: ArrayList<Motto>) {
        Arrow.displayBackArrow(activity as MainActivity)
        if (seriesMottos.isEmpty()) {
            seriesRecycler.visibility = View.GONE
            notFoundMottosTextView.visibility = View.VISIBLE
            mottosLoadingProgressBar.visibility = View.INVISIBLE
        } else {
            notFoundMottosTextView.visibility = View.INVISIBLE
            seriesRecycler.visibility = View.GONE
            mottosLoadingProgressBar.visibility = View.INVISIBLE

            val layoutManager = GridLayoutManager(context, 1)
            val adapter = MottosRecyclerAdapter(seriesMottos)

            seriesMottosRecycler.scheduleLayoutAnimation()
            seriesMottosRecycler.setHasFixedSize(true)
            seriesMottosRecycler.layoutManager = layoutManager
            seriesMottosRecycler.adapter = adapter
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

    private fun handleSeriesRecyclerItemClick() {
        seriesRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), seriesRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedSeries = series[position]
                    seriesDashboardViewModel.putTVSeriesMottosPostValue(clickedSeries)
                    mottosLoadingProgressBar.visibility = View.VISIBLE
                }

                override fun onItemLongClick(view: View, position: Int) {
                    clickedSeries = series[position]
                    seriesDashboardViewModel.putTVSeriesMottosPostValue(clickedSeries)
                    mottosLoadingProgressBar.visibility = View.VISIBLE
                }
            })
        )
    }

    private fun handleSeriesMottosRecyclerItemClick() {
        seriesMottosRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), seriesMottosRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedMotto = seriesMottos[position]

                    AdViewer.displayFullMottoAd(activity as MainActivity, requireContext())
                    DialogViewer.displayFullMottoDialog(
                        requireContext(),
                        fullMottoDialog,
                        clickedMotto,
                        allDbMottos
                    )
                    observeDbMottos()
                }

                override fun onItemLongClick(view: View, position: Int) {
                    clickedMotto = seriesMottos[position]

                    AdViewer.displayFullMottoAd(activity as MainActivity, requireContext())
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
        seriesRecycler = binding.recyclerviewSeriesDashboard
        seriesMottosRecycler = binding.recyclerviewSeriesMottos

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
