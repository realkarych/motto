package github.karchx.motto.views.home

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import github.karchx.motto.R
import github.karchx.motto.databinding.FragmentHomeBinding
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.viewmodels.HomeViewModel
import github.karchx.motto.viewmodels.MottosViewModel
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.tools.adapters.MottosRecyclerAdapter
import github.karchx.motto.views.tools.listeners.OnClickAddToFavouritesListener
import github.karchx.motto.views.tools.listeners.OnClickRecyclerItemListener
import github.karchx.motto.views.tools.managers.AdViewer
import github.karchx.motto.views.tools.managers.Copier
import github.karchx.motto.views.tools.managers.DialogViewer
import github.karchx.motto.views.tools.managers.Toaster
import java.util.*
import github.karchx.motto.models.db.Motto as dbMotto


class HomeFragment : Fragment() {

    private lateinit var mottos: ArrayList<Motto>
    private lateinit var clickedMotto: Motto
    private lateinit var allDbMottos: List<dbMotto>

    private lateinit var mottosViewModel: MottosViewModel
    private lateinit var mMottosLoadingProgressBar: ProgressBar
    private lateinit var mMottosRecycler: RecyclerView
    private lateinit var mSwipeRefreshLayoutRandomMottos: SwipeRefreshLayout
    private lateinit var mFullMottoDialog: Dialog
    private lateinit var mFullMottoCardView: CardView
    private lateinit var mAddToFavouritesImageView: ImageView
    private lateinit var mGlobalScopeMottosEditText: EditText
    private lateinit var mMottosFoundTextView: TextView
    private lateinit var mFindMottosButton: ImageView

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
        initData()
        initViews()

        observeRandomMottos()
        observeRequestMottos()
        observeDbMottos()
        setFullMottoCardViewClickListener()
        setRandomMottosClickListener()
        handleFindMottosClick()
        setAddToFavouritesBtnClickListener()
        handleRecyclerSwipe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleRecyclerSwipe() {
        mSwipeRefreshLayoutRandomMottos.setOnRefreshListener {
            mGlobalScopeMottosEditText.text.clear()
            homeViewModel.putRandomMottosPostValue()
        }
    }

    private fun handleFindMottosClick() {

        mFindMottosButton.setOnClickListener {
            val request = mGlobalScopeMottosEditText.text.toString().trim()
            if (request != "") {
                homeViewModel.putRequestMottosPostValue(request)
                hideKeyboard()
                mMottosLoadingProgressBar.visibility = View.VISIBLE
                mMottosFoundTextView.text = getString(R.string.found_on_request)
            }
        }

        mGlobalScopeMottosEditText.onSubmit {
            val request = mGlobalScopeMottosEditText.text.toString().trim()
            if (request != "") {
                homeViewModel.putRequestMottosPostValue(request)
                hideKeyboard()
                mMottosLoadingProgressBar.visibility = View.VISIBLE
                mMottosFoundTextView.text = getString(R.string.found_on_request)
            }
        }
    }

    private fun observeRequestMottos() {
        homeViewModel.requestMottos.observe(viewLifecycleOwner, { _requestMottos ->
            mottos = _requestMottos
            displayRequestMottosRecycler(mottos)

            if (mSwipeRefreshLayoutRandomMottos.isRefreshing) {
                mSwipeRefreshLayoutRandomMottos.isRefreshing = false
            }
        })
    }

    private fun observeRandomMottos() {
        mMottosLoadingProgressBar.visibility = View.VISIBLE
        homeViewModel.randomMottos.observe(viewLifecycleOwner, { _randomMottos ->
            mottos = _randomMottos
            mMottosFoundTextView.text = getString(R.string.found_random_mottos)
            displayRandomMottosRecycler(mottos)

            if (mSwipeRefreshLayoutRandomMottos.isRefreshing) {
                mSwipeRefreshLayoutRandomMottos.isRefreshing = false
            }
        })
    }

    private fun observeDbMottos() {
        mottosViewModel.allMottos.observe(viewLifecycleOwner) { allMottos ->
            allDbMottos = allMottos
        }
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

    private fun setAddToFavouritesBtnClickListener() {
        mAddToFavouritesImageView.setOnClickListener {
            OnClickAddToFavouritesListener.handleMotto(
                requireContext(),
                mottosViewModel,
                mAddToFavouritesImageView,
                allDbMottos,
                clickedMotto
            )
        }
    }

    private fun setRandomMottosClickListener() {
        mMottosRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), mMottosRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedMotto = mottos[position]

                    AdViewer.displayFullMottoAd(activity as MainActivity, requireContext())
                    DialogViewer.displayFullMottoDialog(
                        requireContext(),
                        mFullMottoDialog,
                        clickedMotto,
                        allDbMottos
                    )
                    observeDbMottos()
                }

                override fun onItemLongClick(view: View, position: Int) {
                    clickedMotto = mottos[position]

                    AdViewer.displayFullMottoAd(activity as MainActivity, requireContext())
                    DialogViewer.displayFullMottoDialog(
                        requireContext(),
                        mFullMottoDialog,
                        clickedMotto,
                        allDbMottos
                    )
                    observeDbMottos()
                }
            })
        )
    }

    private fun displayRequestMottosRecycler(mottos: ArrayList<Motto>) {
        mMottosLoadingProgressBar.visibility = View.INVISIBLE

        val adapter = MottosRecyclerAdapter(mottos)
        mMottosRecycler.adapter = adapter

        if (mottos.isEmpty())
            mMottosFoundTextView.text = getString(R.string.not_found_on_request)
    }

    private fun displayRandomMottosRecycler(mottos: ArrayList<Motto>) {
        mMottosLoadingProgressBar.visibility = View.INVISIBLE
        hideKeyboard()
        mGlobalScopeMottosEditText.isCursorVisible = false

        val layoutManager = GridLayoutManager(context, 1)
        mMottosRecycler.scheduleLayoutAnimation()
        mMottosRecycler.setHasFixedSize(true)
        mMottosRecycler.layoutManager = layoutManager
        val adapter = MottosRecyclerAdapter(mottos)
        mMottosRecycler.adapter = adapter

        mMottosRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_SETTLING)
                    if (dy > 0) {
                        mMottosFoundTextView.animate().translationY(1f)
                        mMottosFoundTextView.visibility = View.GONE
                    } else if (dy < 0) {
                        mMottosFoundTextView.animate().translationY(0f)
                        mMottosFoundTextView.visibility = View.VISIBLE
                    }
            }
        })

        if (mottos.isEmpty()) {
            mMottosLoadingProgressBar.visibility = View.INVISIBLE
            mMottosFoundTextView.text = getString(R.string.not_found_random_mottos)
        }
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = requireActivity().currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun EditText.onSubmit(func: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                func()
            }
            true
        }
    }

    private fun initData() {
        mottosViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(MottosViewModel(application = requireActivity().application)::class.java)
    }

    private fun initViews() {
        mMottosLoadingProgressBar = binding.progressbarMottosLoading
        mMottosFoundTextView = binding.textviewMottosFound
        mMottosRecycler = binding.recyclerviewMottos
        mSwipeRefreshLayoutRandomMottos = binding.refreshContainerOfRecyclerviewRandomMottos
        mGlobalScopeMottosEditText = binding.edittextGlobalScopeMottos
        mFindMottosButton = binding.findRandomMottosButton

        mFullMottoDialog = Dialog(requireActivity())
        mFullMottoDialog.setContentView(R.layout.dialog_full_motto)
        mFullMottoCardView = mFullMottoDialog.findViewById(R.id.cardview_full_motto_item)
        mAddToFavouritesImageView = mFullMottoDialog.findViewById(R.id.imageview_is_saved_motto)
    }
}
