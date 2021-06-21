package github.karchx.motto.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.R
import github.karchx.motto.databinding.FragmentDashboardBinding
import github.karchx.motto.search_engine.citaty_info_website.data.Author
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.search_engine.citaty_info_website.data.Topic
import github.karchx.motto.storages.Constants
import github.karchx.motto.ui.adapters.MottosRecyclerAdapter
import github.karchx.motto.ui.dashboard.adapters.AuthorsRecyclerAdapter
import github.karchx.motto.ui.dashboard.adapters.TopicsRecyclerAdapter
import github.karchx.motto.ui.listeners.OnClickRecyclerItemListener

class DashboardFragment : Fragment() {

    private lateinit var authors: ArrayList<Author>
    private lateinit var topics: ArrayList<Topic>
    private lateinit var clickedAuthor: Author
    private lateinit var clickedTopic: Topic
    private lateinit var authorMottos: ArrayList<Motto>
    private lateinit var topicMottos: ArrayList<Motto>

    private lateinit var mAuthorsRecycler: RecyclerView
    private lateinit var mTopicsRecycler: RecyclerView
    private lateinit var mAuthorMottosRecycler: RecyclerView
    private lateinit var mTopicMottosRecycler: RecyclerView

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
        initViews()

        dashboardViewModel.authors.observe(viewLifecycleOwner, { _authors ->
            authors = _authors
            arguments?.takeIf { it.containsKey(Constants.KEYWORD_MOTTO_TYPE) }?.apply {
                if (getString(Constants.KEYWORD_MOTTO_TYPE) == resources.getString(R.string.authors)) {
                    displayAuthorsRecycler(authors)
                }
            }
        })
        dashboardViewModel.topics.observe(viewLifecycleOwner, { _topics ->
            topics = _topics
            arguments?.takeIf { it.containsKey(Constants.KEYWORD_MOTTO_TYPE) }?.apply {
                if (getString(Constants.KEYWORD_MOTTO_TYPE) == resources.getString(R.string.topics)) {
                    displayTopicsRecycler(topics)
                }
            }
        })
        dashboardViewModel.authorMottos.observe(viewLifecycleOwner, { _authorMottos ->
            authorMottos = _authorMottos
            displayAuthorMottosRecycler(authorMottos)
        })
        dashboardViewModel.topicMottos.observe(viewLifecycleOwner, { _topicMottos ->
            topicMottos = _topicMottos
            displayTopicMottosRecycler(topicMottos)
        })

        mAuthorsRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedAuthor = authors[position]
                    dashboardViewModel.putAuthorMottosPostValue(clickedAuthor)
                }
            })
        )
        mTopicsRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedTopic = topics[position]
                    dashboardViewModel.putTopicMottosPostValue(clickedTopic)
                }
            })
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayAuthorsRecycler(authors: ArrayList<Author>) {
        val layoutManager = GridLayoutManager(context, 2)
        val adapter = AuthorsRecyclerAdapter(this@DashboardFragment, authors)

        mAuthorsRecycler.setHasFixedSize(true)
        mAuthorsRecycler.layoutManager = layoutManager
        mAuthorsRecycler.adapter = adapter
    }

    private fun displayTopicsRecycler(topics: ArrayList<Topic>) {
        val layoutManager = GridLayoutManager(context, 2)
        val adapter = TopicsRecyclerAdapter(this@DashboardFragment, topics)

        mTopicsRecycler.setHasFixedSize(true)
        mTopicsRecycler.layoutManager = layoutManager
        mTopicsRecycler.adapter = adapter
    }

    private fun displayAuthorMottosRecycler(authorMottos: ArrayList<Motto>) {
        // Hide authors recyclerview
        mAuthorsRecycler.visibility = View.GONE

        // Display author mottos recyclerview
        val layoutManager = GridLayoutManager(context, 1)
        val adapter = MottosRecyclerAdapter(authorMottos)

        mAuthorMottosRecycler.setHasFixedSize(true)
        mAuthorMottosRecycler.layoutManager = layoutManager
        mAuthorMottosRecycler.adapter = adapter
    }

    private fun displayTopicMottosRecycler(topicMottos: ArrayList<Motto>) {
        // Hide topics recyclerview
        mTopicsRecycler.visibility = View.GONE

        // Display author mottos recyclerview
        val layoutManager = GridLayoutManager(context, 1)
        val adapter = MottosRecyclerAdapter(topicMottos)

        mTopicMottosRecycler.setHasFixedSize(true)
        mTopicMottosRecycler.layoutManager = layoutManager
        mTopicMottosRecycler.adapter = adapter
    }

    private fun initViews() {
        mAuthorsRecycler = binding.recyclerviewAuthorsDashboard
        mTopicsRecycler = binding.recyclerviewTopicsDashboard
        mAuthorMottosRecycler = binding.recyclerviewAuthorMottos
        mTopicMottosRecycler = binding.recyclerviewTopicMottos
    }
}
