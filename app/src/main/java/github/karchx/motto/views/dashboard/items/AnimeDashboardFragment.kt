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
import github.karchx.motto.ads.AdViewer
import github.karchx.motto.copying.Copier
import github.karchx.motto.databinding.FragmentAnimeDashboardBinding
import github.karchx.motto.models.db.saved_motto.SavedMotto
import github.karchx.motto.models.user_settings.UserPrefs
import github.karchx.motto.search_engine.citaty_info_website.UIMotto
import github.karchx.motto.search_engine.citaty_info_website.items.Anime
import github.karchx.motto.viewmodels.SavedMottosViewModel
import github.karchx.motto.viewmodels.dashboard.anime.AnimeDashboardViewModel
import github.karchx.motto.viewmodels.dashboard.anime.AnimeFactory
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.tools.adapters.AnimeRecyclerAdapter
import github.karchx.motto.views.tools.adapters.MottosRecyclerAdapter
import github.karchx.motto.views.tools.listeners.OnClickAddToFavouritesListener
import github.karchx.motto.views.tools.listeners.OnClickRecyclerItemListener
import github.karchx.motto.views.tools.managers.*


class AnimeDashboardFragment : Fragment(R.layout.fragment_anime_dashboard) {

    private var _binding: FragmentAnimeDashboardBinding? = null
    private val binding get() = _binding!!

    // ViewModels
    private lateinit var animeDashboardViewModel: AnimeDashboardViewModel
    private lateinit var savedMottosViewModel: SavedMottosViewModel

    // Views
    private lateinit var animeRecycler: RecyclerView
    private lateinit var animeMottosRecycler: RecyclerView
    private lateinit var mottosLoadingProgressBar: ProgressBar
    private lateinit var fullMottoCardView: CardView
    private lateinit var fullMottoDialog: Dialog
    private lateinit var addToFavouritesImageView: ImageView
    private lateinit var notFoundMottosTextView: TextView

    //Data
    private lateinit var userPrefs: UserPrefs
    private lateinit var anime: ArrayList<Anime>
    private lateinit var clickedAnime: Anime
    private lateinit var allDbMottos: List<SavedMotto>
    private lateinit var animeMottos: ArrayList<UIMotto>
    private lateinit var clickedMotto: UIMotto

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        savedMottosViewModel.allMottos.observe(viewLifecycleOwner) { allMottos ->
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

    private fun displayAnimeMottosRecycler(animeMottos: ArrayList<UIMotto>) {
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
                savedMottosViewModel,
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

                override fun onItemLongClick(view: View, position: Int) {}
            })
        )
    }

    private fun handleAnimeMottosRecyclerItemClick() {
        animeMottosRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), animeMottosRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedMotto = animeMottos[position]

                    AdViewer(activity as MainActivity, requireContext()).displayFullMottoAd()
                    DialogViewer.displayFullMottoDialog(
                        requireContext(),
                        fullMottoDialog,
                        clickedMotto,
                        allDbMottos
                    )
                    observeDbMottos()
                }

                override fun onItemLongClick(view: View, position: Int) {}
            })
        )
    }

    private fun handleCopyMottoData() {
        fullMottoCardView.setOnClickListener {
            val text = Copier(activity as MainActivity, requireContext()).getMottoDataToCopy(
                quote = clickedMotto.quote,
                source = clickedMotto.source,
                isCopyWithAuthor = userPrefs.copySettings.isWithSource()
            )

            Copier(activity as MainActivity, requireContext()).copyText(text)
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
        userPrefs = UserPrefs(activity as MainActivity)
        animeDashboardViewModel =
            ViewModelProvider(
                this,
                AnimeFactory(requireActivity().application, userPrefs)
            )[AnimeDashboardViewModel::class.java]

        savedMottosViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(SavedMottosViewModel(application = requireActivity().application)::class.java)
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
