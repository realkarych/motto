package github.karchx.motto.ui.dashboard

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.ui.MainActivity
import github.karchx.motto.R
import github.karchx.motto.databinding.FragmentDashboardBinding
import github.karchx.motto.search_engine.citaty_info_website.data.Author
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.search_engine.citaty_info_website.data.Topic
import github.karchx.motto.model.storages.Constants
import github.karchx.motto.ui.tools.adapters.MottosRecyclerAdapter
import github.karchx.motto.ui.dashboard.adapters.AuthorsRecyclerAdapter
import github.karchx.motto.ui.dashboard.adapters.TopicsRecyclerAdapter
import github.karchx.motto.ui.tools.listeners.OnClickRecyclerItemListener
import github.karchx.motto.ui.tools.managers.Copier
import github.karchx.motto.ui.tools.managers.DialogViewer
import github.karchx.motto.ui.tools.managers.Toaster
import github.karchx.motto.viewmodels.DashboardViewModel

class DashboardFragment : Fragment() {

    private lateinit var authors: ArrayList<Author>
    private lateinit var topics: ArrayList<Topic>
    private lateinit var clickedAuthor: Author
    private lateinit var clickedTopic: Topic
    private lateinit var authorMottos: ArrayList<Motto>
    private lateinit var topicMottos: ArrayList<Motto>
    private lateinit var clickedMotto: Motto

    private lateinit var mAuthorsRecycler: RecyclerView
    private lateinit var mTopicsRecycler: RecyclerView
    private lateinit var mAuthorMottosRecycler: RecyclerView
    private lateinit var mTopicMottosRecycler: RecyclerView
    private lateinit var mFullMottoDialog: Dialog
    private lateinit var mFullMottoCardView: CardView
    private lateinit var mMottosLoadingProgressBar: ProgressBar

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

    private fun handleAuthorsRecyclerItemClick() {
        mAuthorsRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedAuthor = authors[position]
                    dashboardViewModel.putAuthorMottosPostValue(clickedAuthor)
                    mMottosLoadingProgressBar.visibility = View.VISIBLE
                }
            })
        )
    }

    private fun handleTopicsRecyclerItemClick() {
        mTopicsRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedTopic = topics[position]
                    dashboardViewModel.putTopicMottosPostValue(clickedTopic)
                    mMottosLoadingProgressBar.visibility = View.VISIBLE
                }
            })
        )
    }

    private fun handleAuthorMottosRecyclerItemClick() {
        mAuthorMottosRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedMotto = authorMottos[position]
                    DialogViewer.displayFullMottoDialog(mFullMottoDialog, clickedMotto)
                }
            })
        )
    }

    private fun handleTopicMottosRecyclerItemClick() {
        mTopicMottosRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedMotto = topicMottos[position]
                    DialogViewer.displayFullMottoDialog(mFullMottoDialog, clickedMotto)
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
                if (getString(Constants.KEYWORD_MOTTO_TYPE) == resources.getString(R.string.authors)) {
                    displayAuthorsRecycler(authors)
                }
            }
        })
    }

    private fun setTopicsObserver() {
        dashboardViewModel.topics.observe(viewLifecycleOwner, { _topics ->
            topics = _topics
            arguments?.takeIf { it.containsKey(Constants.KEYWORD_MOTTO_TYPE) }?.apply {
                if (getString(Constants.KEYWORD_MOTTO_TYPE) == resources.getString(R.string.topics)) {
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
        mAuthorsRecycler.visibility = View.GONE
        mMottosLoadingProgressBar.visibility = View.INVISIBLE

        val layoutManager = GridLayoutManager(context, 1)
        val adapter = MottosRecyclerAdapter(authorMottos)

        mAuthorMottosRecycler.setHasFixedSize(true)
        mAuthorMottosRecycler.layoutManager = layoutManager
        mAuthorMottosRecycler.adapter = adapter
    }

    private fun displayTopicMottosRecycler(topicMottos: ArrayList<Motto>) {
        displayBackArrow()
        mTopicsRecycler.visibility = View.GONE
        mMottosLoadingProgressBar.visibility = View.INVISIBLE

        val layoutManager = GridLayoutManager(context, 1)
        val adapter = MottosRecyclerAdapter(topicMottos)

        mTopicMottosRecycler.setHasFixedSize(true)
        mTopicMottosRecycler.layoutManager = layoutManager
        mTopicMottosRecycler.adapter = adapter
    }

    private fun setObservers() {
        setAuthorsObserver()
        setTopicsObserver()
        setAuthorMottosObserver()
        setTopicMottosObserver()
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
    }
}
