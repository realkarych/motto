package github.karchx.motto.views.dashboard.items

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
import github.karchx.motto.databinding.FragmentAnimeDashboardBinding
import github.karchx.motto.search_engine.citaty_info_website.data.Anime
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.viewmodels.dashboard.AnimeDashboardViewModel
import github.karchx.motto.viewmodels.MottosViewModel
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.tools.adapters.AnimeRecyclerAdapter
import github.karchx.motto.views.tools.adapters.MottosRecyclerAdapter
import github.karchx.motto.views.tools.listeners.OnClickAddToFavouritesListener
import github.karchx.motto.views.tools.listeners.OnClickRecyclerItemListener
import github.karchx.motto.views.tools.managers.*
import github.karchx.motto.models.db.Motto as dbMotto

class AnimeDashboardFragment : Fragment(R.layout.fragment_anime_dashboard) {

    private var _binding: FragmentAnimeDashboardBinding? = null
    private val binding get() = _binding!!

    // ViewModels
    private lateinit var animeDashboardViewModel: AnimeDashboardViewModel
    private lateinit var mottosViewModel: MottosViewModel

    // Views
    private lateinit var animeRecycler: RecyclerView
    private lateinit var animeMottosRecycler: RecyclerView
    private lateinit var mottosLoadingProgressBar: ProgressBar
    private lateinit var fullMottoCardView: CardView
    private lateinit var fullMottoDialog: Dialog
    private lateinit var addToFavouritesImageView: ImageView
    private lateinit var notFoundMottosTextView: TextView

    //Data
    private lateinit var anime: ArrayList<Anime>
    private lateinit var clickedAnime: Anime
    private lateinit var allDbMottos: List<dbMotto>
    private lateinit var animeMottos: ArrayList<Motto>
    private lateinit var clickedMotto: Motto

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        animeDashboardViewModel = ViewModelProvider(this).get(AnimeDashboardViewModel::class.java)
        _binding = FragmentAnimeDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        initData()
        initViews()

        observeAnime()
        observeAnimeMottos()
        observeDbMottos()

        handleAnimeRecyclerItemClick()
        handleAnimeMottosRecyclerItemClick()

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

    private fun observeAnime() {
        animeDashboardViewModel.anime.observe(viewLifecycleOwner, { _anime ->
            anime = _anime
            displayAnimeRecycler(anime)
        })
    }

    private fun observeAnimeMottos() {
        animeDashboardViewModel.animeMottos.observe(viewLifecycleOwner, { _animeMottos ->
            animeMottos = _animeMottos
            displayAnimeMottosRecycler(animeMottos)
        })
    }

    private fun observeDbMottos() {
        mottosViewModel.allMottos.observe(viewLifecycleOwner) { allMottos ->
            allDbMottos = allMottos
        }
    }

    private fun displayAnimeRecycler(anime: ArrayList<Anime>) {
        Arrow.hideBackArrow(activity as MainActivity)

        val layoutManager = GridLayoutManager(context, 2)
        val adapter = AnimeRecyclerAdapter(this@AnimeDashboardFragment, anime)

        animeRecycler.setHasFixedSize(true)
        animeRecycler.layoutManager = layoutManager
        animeRecycler.adapter = adapter
    }

    private fun displayAnimeMottosRecycler(animeMottos: ArrayList<Motto>) {
        Arrow.displayBackArrow(activity as MainActivity)
        if (animeMottos.isEmpty()) {
            animeRecycler.visibility = View.GONE
            notFoundMottosTextView.visibility = View.VISIBLE
            mottosLoadingProgressBar.visibility = View.INVISIBLE
        } else {
            notFoundMottosTextView.visibility = View.INVISIBLE
            animeRecycler.visibility = View.GONE
            mottosLoadingProgressBar.visibility = View.INVISIBLE

            val layoutManager = GridLayoutManager(context, 1)
            val adapter = MottosRecyclerAdapter(animeMottos)

            animeMottosRecycler.scheduleLayoutAnimation()
            animeMottosRecycler.setHasFixedSize(true)
            animeMottosRecycler.layoutManager = layoutManager
            animeMottosRecycler.adapter = adapter
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

    private fun handleAnimeRecyclerItemClick() {
        animeRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), animeRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedAnime = anime[position]
                    animeDashboardViewModel.putAnimeMottosPostValue(clickedAnime)
                    mottosLoadingProgressBar.visibility = View.VISIBLE
                }

                override fun onItemLongClick(view: View, position: Int) {
                    clickedAnime = anime[position]
                    animeDashboardViewModel.putAnimeMottosPostValue(clickedAnime)
                    mottosLoadingProgressBar.visibility = View.VISIBLE
                }
            })
        )
    }

    private fun handleAnimeMottosRecyclerItemClick() {
        animeMottosRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), animeMottosRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedMotto = animeMottos[position]

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
                    clickedMotto = animeMottos[position]

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
        mottosViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(MottosViewModel(application = requireActivity().application)::class.java)
    }

    private fun initViews() {
        animeRecycler = binding.recyclerviewAnimeDashboard
        animeMottosRecycler = binding.recyclerviewAnimeMottos

        mottosLoadingProgressBar = binding.progressbarMottosLoading

        fullMottoDialog = Dialog(requireActivity())
        fullMottoDialog.setContentView(R.layout.dialog_full_motto)

        fullMottoCardView = fullMottoDialog.findViewById(R.id.cardview_full_motto_item)
        addToFavouritesImageView = fullMottoDialog.findViewById(R.id.imageview_is_saved_motto)

        notFoundMottosTextView = binding.textviewMottosNotFoundDashboard
    }
}
