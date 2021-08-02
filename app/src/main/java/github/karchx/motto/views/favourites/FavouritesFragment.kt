package github.karchx.motto.views.favourites

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.R
import github.karchx.motto.copying.Copier
import github.karchx.motto.databinding.FragmentFavouritesBinding
import github.karchx.motto.models.db.saved_motto.SavedMotto
import github.karchx.motto.models.user_settings.UserPrefs
import github.karchx.motto.search_engine.citaty_info_website.UIMotto
import github.karchx.motto.viewmodels.SavedMottosViewModel
import github.karchx.motto.viewmodels.favourites.FavouritesViewModel
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.tools.adapters.FromDbMottosRecyclerAdapter
import github.karchx.motto.views.tools.listeners.OnClickAddToFavouritesListener
import github.karchx.motto.views.tools.listeners.OnClickRecyclerItemListener
import github.karchx.motto.views.tools.managers.DialogViewer
import github.karchx.motto.views.tools.managers.Toaster

class FavouritesFragment : Fragment() {

    private lateinit var favouritesViewModel: FavouritesViewModel
    private lateinit var savedMottosViewModel: SavedMottosViewModel
    private lateinit var mFullMottoDialog: Dialog
    private lateinit var mFullMottoCardView: CardView

    private lateinit var mFavouriteMottosRecycler: RecyclerView
    private lateinit var mAddToFavouritesImageView: ImageView
    private lateinit var mSavedMottosInfoTextView: TextView

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var userPrefs: UserPrefs
    private lateinit var savedMottos: List<SavedMotto>
    private lateinit var clickedMotto: SavedMotto

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

        observeDbMottos()
        setAddToFavouritesBtnClickListener()
        setFavouriteMottosClickListener()
        setFullMottoCardViewClickListener()
    }

    private fun observeDbMottos() {
        savedMottosViewModel.allMottos.observe(viewLifecycleOwner) { mottos ->
            savedMottos = mottos.reversed()
            if (mottos.isEmpty()) {
                mSavedMottosInfoTextView.text = getString(R.string.not_saved_any_mottos)
                mFavouriteMottosRecycler.visibility = View.INVISIBLE
            } else {
                mSavedMottosInfoTextView.text = getString(R.string.how_to_delete_motto)
                mFavouriteMottosRecycler.visibility = View.VISIBLE
                displayFavouriteMottos(savedMottos)
            }
        }
    }

    private fun setFavouriteMottosClickListener() {
        mFavouriteMottosRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), mFavouriteMottosRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedMotto = savedMottos[position]
                    DialogViewer.displayFullMottoDialog(
                        requireContext(),
                        mFullMottoDialog,
                        UIMotto(clickedMotto.quote, clickedMotto.source),
                        savedMottos
                    )
                    observeDbMottos()
                }

                override fun onItemLongClick(view: View, position: Int) {
                    clickedMotto = savedMottos[position]
                    savedMottosViewModel.deleteMotto(clickedMotto.quote, clickedMotto.source)
                    observeDbMottos()
                }
            })
        )
    }

    private fun setFullMottoCardViewClickListener() {
        mFullMottoCardView.setOnClickListener {
            val text = Copier(activity as MainActivity, requireContext()).getMottoDataToCopy(
                quote = clickedMotto.quote,
                source = clickedMotto.source,
                isCopyWithAuthor = userPrefs.copySettings.isWithSource()
            )

            Copier(activity as MainActivity, requireContext()).copyText(text)
            Toaster.displayTextIsCopiedToast(requireContext())
        }
    }

    private fun setAddToFavouritesBtnClickListener() {
        mAddToFavouritesImageView.setOnClickListener {
            OnClickAddToFavouritesListener.handleMotto(
                requireContext(),
                savedMottosViewModel,
                mAddToFavouritesImageView,
                savedMottos,
                UIMotto(clickedMotto.quote, clickedMotto.source)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayFavouriteMottos(mottos: List<SavedMotto>) {
        val layoutManager = GridLayoutManager(context, 1)
        val adapter = FromDbMottosRecyclerAdapter(mottos)
        mFavouriteMottosRecycler.setHasFixedSize(true)
        mFavouriteMottosRecycler.layoutManager = layoutManager
        mFavouriteMottosRecycler.adapter = adapter

        mFavouriteMottosRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_SETTLING)
                    if (dy > 0) {
                        mSavedMottosInfoTextView.animate().translationY(1f)
                        mSavedMottosInfoTextView.visibility = View.GONE
                    } else if (dy < 0) {
                        mSavedMottosInfoTextView.animate().translationY(0f)
                        mSavedMottosInfoTextView.visibility = View.VISIBLE
                    }
            }
        })
    }

    private fun initData() {
        favouritesViewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)

        savedMottosViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(SavedMottosViewModel(application = requireActivity().application)::class.java)

        userPrefs = UserPrefs(activity as MainActivity)
    }

    private fun initViews() {
        mFavouriteMottosRecycler = binding.recyclerviewFavouriteMottos
        mFullMottoDialog = Dialog(requireActivity())
        mFullMottoDialog.setContentView(R.layout.dialog_full_motto)
        mFullMottoCardView = mFullMottoDialog.findViewById(R.id.cardview_full_motto_item)
        mAddToFavouritesImageView = mFullMottoDialog.findViewById(R.id.imageview_is_saved_motto)
        mSavedMottosInfoTextView = binding.textviewSavedMottosInfo
    }
}
