package github.karchx.motto.views.dashboard

import android.app.Dialog
import android.os.Bundle
import android.view.*
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
import github.karchx.motto.databinding.FragmentDashboardBinding
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.search_engine.citaty_info_website.data.Author
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.search_engine.citaty_info_website.data.Topic
import github.karchx.motto.viewmodels.DashboardViewModel
import github.karchx.motto.viewmodels.MottosViewModel
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.tools.adapters.AuthorsRecyclerAdapter
import github.karchx.motto.views.tools.adapters.MottosRecyclerAdapter
import github.karchx.motto.views.tools.adapters.TopicsRecyclerAdapter
import github.karchx.motto.views.tools.listeners.OnClickAddToFavouritesListener
import github.karchx.motto.views.tools.listeners.OnClickRecyclerItemListener
import github.karchx.motto.views.tools.managers.Copier
import github.karchx.motto.views.tools.managers.DialogViewer
import github.karchx.motto.views.tools.managers.Toaster
import github.karchx.motto.models.db.Motto as dbMotto

class DashboardFragment : Fragment() {

    private lateinit var authors: ArrayList<Author>
    private lateinit var topics: ArrayList<Topic>
    private lateinit var clickedAuthor: Author
    private lateinit var clickedTopic: Topic
    private lateinit var allDbMottos: List<dbMotto>
    private lateinit var authorMottos: ArrayList<Motto>
    private lateinit var topicMottos: ArrayList<Motto>
    private lateinit var clickedMotto: Motto
    private lateinit var mottosViewModel: MottosViewModel
    private lateinit var mAuthorsRecycler: RecyclerView
    private lateinit var mTopicsRecycler: RecyclerView
    private lateinit var mAuthorMottosRecycler: RecyclerView
    private lateinit var mTopicMottosRecycler: RecyclerView
    private lateinit var mFullMottoDialog: Dialog
    private lateinit var mFullMottoCardView: CardView
    private lateinit var mAddToFavouritesImageView: ImageView
    private lateinit var mMottosLoadingProgressBar: ProgressBar
    private lateinit var mNotFoundMottosTextView: TextView

    private lateinit var dashboardViewModel: DashboardViewModel

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        initViews()
        setObservers()
        handleAuthorsRecyclerItemClick()
        handleTopicsRecyclerItemClick()
        handleAuthorMottosRecyclerItemClick()
        handleTopicMottosRecyclerItemClick()
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

    private fun displayBackArrow() {
        (activity as MainActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity?)?.supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun hideBackArrow() {
        (activity as MainActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity?)?.supportActionBar?.setDisplayShowHomeEnabled(false)
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

    private fun handleAuthorsRecyclerItemClick() {
        mAuthorsRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), mAuthorsRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedAuthor = authors[position]
                    dashboardViewModel.putAuthorMottosPostValue(clickedAuthor)
                    mMottosLoadingProgressBar.visibility = View.VISIBLE
                }

                override fun onItemLongClick(view: View, position: Int) {
                    clickedAuthor = authors[position]
                    dashboardViewModel.putAuthorMottosPostValue(clickedAuthor)
                    mMottosLoadingProgressBar.visibility = View.VISIBLE
                }
            })
        )
    }

    private fun handleTopicsRecyclerItemClick() {
        mTopicsRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), mTopicsRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedTopic = topics[position]
                    dashboardViewModel.putTopicMottosPostValue(clickedTopic)
                    mMottosLoadingProgressBar.visibility = View.VISIBLE
                }

                override fun onItemLongClick(view: View, position: Int) {
                    clickedTopic = topics[position]
                    dashboardViewModel.putTopicMottosPostValue(clickedTopic)
                    mMottosLoadingProgressBar.visibility = View.VISIBLE
                }
            })
        )
    }

    private fun handleAuthorMottosRecyclerItemClick() {
        mAuthorMottosRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), mAuthorMottosRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedMotto = authorMottos[position]
                    DialogViewer.displayFullMottoDialog(
                        requireContext(),
                        mFullMottoDialog,
                        clickedMotto,
                        allDbMottos
                    )
                    observeDbMottos()
                }

                override fun onItemLongClick(view: View, position: Int) {
                    clickedMotto = authorMottos[position]
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

    private fun handleTopicMottosRecyclerItemClick() {
        mTopicMottosRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), mTopicMottosRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedMotto = topicMottos[position]
                    DialogViewer.displayFullMottoDialog(
                        requireContext(),
                        mFullMottoDialog,
                        clickedMotto,
                        allDbMottos
                    )
                    observeDbMottos()
                }

                override fun onItemLongClick(view: View, position: Int) {
                    clickedMotto = topicMottos[position]
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

    private fun handleCopyMottoData() {
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

    private fun setAuthorsObserver() {
        dashboardViewModel.authors.observe(viewLifecycleOwner, { _authors ->
            authors = _authors
            arguments?.takeIf { it.containsKey(Constants.KEYWORD_MOTTO_TYPE) }?.apply {
                val authorsKey = Constants.getMottoTypesNames(requireContext())[0]

                    if (getString(Constants.KEYWORD_MOTTO_TYPE) == authorsKey) {
                    displayAuthorsRecycler(authors)
                }
            }
        })
    }

    private fun setTopicsObserver() {
        dashboardViewModel.topics.observe(viewLifecycleOwner, { _topics ->
            topics = _topics
            arguments?.takeIf { it.containsKey(Constants.KEYWORD_MOTTO_TYPE) }?.apply {
                val  topicsKey = Constants.getMottoTypesNames(requireContext())[1]

                if (getString(Constants.KEYWORD_MOTTO_TYPE) == topicsKey) {
                    displayTopicsRecycler(topics)
                }
            }
        })
    }

    private fun setAuthorMottosObserver() {
        dashboardViewModel.authorMottos.observe(viewLifecycleOwner, { _authorMottos ->
            authorMottos = _authorMottos
            displayAuthorMottosRecycler(authorMottos)
        })
    }

    private fun setTopicMottosObserver() {
        dashboardViewModel.topicMottos.observe(viewLifecycleOwner, { _topicMottos ->
            topicMottos = _topicMottos
            displayTopicMottosRecycler(topicMottos)
        })
    }

    private fun displayAuthorsRecycler(authors: ArrayList<Author>) {
        hideBackArrow()

        val layoutManager = GridLayoutManager(context, 2)
        val adapter = AuthorsRecyclerAdapter(this@DashboardFragment, authors)

        mAuthorsRecycler.setHasFixedSize(true)
        mAuthorsRecycler.layoutManager = layoutManager
        mAuthorsRecycler.adapter = adapter
    }

    private fun displayTopicsRecycler(topics: ArrayList<Topic>) {
        hideBackArrow()

        val layoutManager = GridLayoutManager(context, 2)
        val adapter = TopicsRecyclerAdapter(this@DashboardFragment, topics)

        mTopicsRecycler.setHasFixedSize(true)
        mTopicsRecycler.layoutManager = layoutManager
        mTopicsRecycler.adapter = adapter
    }

    private fun displayAuthorMottosRecycler(authorMottos: ArrayList<Motto>) {
        displayBackArrow()
        if (authorMottos.isEmpty()) {
            mAuthorsRecycler.visibility = View.GONE
            mNotFoundMottosTextView.visibility = View.VISIBLE
            mMottosLoadingProgressBar.visibility = View.INVISIBLE
        } else {
            mNotFoundMottosTextView.visibility = View.INVISIBLE
            mAuthorsRecycler.visibility = View.GONE
            mMottosLoadingProgressBar.visibility = View.INVISIBLE

            val layoutManager = GridLayoutManager(context, 1)
            val adapter = MottosRecyclerAdapter(authorMottos)

            mAuthorMottosRecycler.scheduleLayoutAnimation()
            mAuthorMottosRecycler.setHasFixedSize(true)
            mAuthorMottosRecycler.layoutManager = layoutManager
            mAuthorMottosRecycler.adapter = adapter
        }
    }

    private fun displayTopicMottosRecycler(topicMottos: ArrayList<Motto>) {
        displayBackArrow()
        if (topicMottos.isEmpty()) {
            mTopicsRecycler.visibility = View.GONE
            mNotFoundMottosTextView.visibility = View.VISIBLE
            mMottosLoadingProgressBar.visibility = View.INVISIBLE
        } else {
            mNotFoundMottosTextView.visibility = View.INVISIBLE
            mTopicsRecycler.visibility = View.GONE
            mMottosLoadingProgressBar.visibility = View.INVISIBLE

            val layoutManager = GridLayoutManager(context, 1)
            val adapter = MottosRecyclerAdapter(topicMottos)

            mTopicMottosRecycler.scheduleLayoutAnimation()
            mTopicMottosRecycler.setHasFixedSize(true)
            mTopicMottosRecycler.layoutManager = layoutManager
            mTopicMottosRecycler.adapter = adapter
        }
    }

    private fun observeDbMottos() {
        mottosViewModel.allMottos.observe(viewLifecycleOwner) { allMottos ->
            allDbMottos = allMottos
        }
    }

    private fun setObservers() {
        setAuthorsObserver()
        setTopicsObserver()
        setAuthorMottosObserver()
        setTopicMottosObserver()
        observeDbMottos()
    }

    private fun initViews() {
        mAuthorsRecycler = binding.recyclerviewAuthorsDashboard
        mTopicsRecycler = binding.recyclerviewTopicsDashboard
        mAuthorMottosRecycler = binding.recyclerviewAuthorMottos
        mTopicMottosRecycler = binding.recyclerviewTopicMottos
        mMottosLoadingProgressBar = binding.progressbarMottosLoading

        mFullMottoDialog = Dialog(requireActivity())
        mFullMottoDialog.setContentView(R.layout.dialog_full_motto)
        mFullMottoCardView = mFullMottoDialog.findViewById(R.id.cardview_full_motto_item)
        mAddToFavouritesImageView = mFullMottoDialog.findViewById(R.id.imageview_is_saved_motto)
        mNotFoundMottosTextView = binding.textviewMottosNotFoundDashboard

        mottosViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(MottosViewModel(application = requireActivity().application)::class.java)
    }
}
