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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.R
import github.karchx.motto.databinding.FragmentAuthorsBinding
import github.karchx.motto.search_engine.citaty_info_website.data.Author
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.viewmodels.AuthorsViewModel
import github.karchx.motto.viewmodels.MottosViewModel
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.tools.adapters.AuthorsRecyclerAdapter
import github.karchx.motto.views.tools.adapters.MottosRecyclerAdapter
import github.karchx.motto.views.tools.listeners.OnClickAddToFavouritesListener
import github.karchx.motto.views.tools.listeners.OnClickRecyclerItemListener
import github.karchx.motto.views.tools.managers.*
import github.karchx.motto.models.db.Motto as dbMotto

class AuthorsFragment : Fragment(R.layout.fragment_authors) {

    private var _binding: FragmentAuthorsBinding? = null
    private val binding get() = _binding!!

    // ViewModels
    private lateinit var authorsViewModel: AuthorsViewModel
    private lateinit var mottosViewModel: MottosViewModel

    // Views
    private lateinit var authorsRecycler: RecyclerView
    private lateinit var authorMottosRecycler: RecyclerView
    private lateinit var mottosLoadingProgressBar: ProgressBar
    private lateinit var fullMottoCardView: CardView
    private lateinit var fullMottoDialog: Dialog
    private lateinit var addToFavouritesImageView: ImageView
    private lateinit var notFoundMottosTextView: TextView

    //Data
    private lateinit var authors: ArrayList<Author>
    private lateinit var clickedAuthor: Author
    private lateinit var allDbMottos: List<dbMotto>
    private lateinit var authorMottos: ArrayList<Motto>
    private lateinit var clickedMotto: Motto

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        authorsViewModel = ViewModelProvider(this).get(AuthorsViewModel::class.java)
        _binding = FragmentAuthorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        initData()
        initViews()

        observeAuthors()
        observeAuthorMottos()
        observeDbMottos()

        handleAuthorsRecyclerItemClick()
        handleAuthorMottosRecyclerItemClick()

        handleCopyMottoData()
        setAddToFavouritesBtnClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            FragmentReload.reload(this, R.layout.fragment_authors)
        }
        return true
    }

    private fun observeAuthors() {
        authorsViewModel.authors.observe(viewLifecycleOwner, { _authors ->
            authors = _authors
            displayAuthorsRecycler(authors)
        })
    }

    private fun observeAuthorMottos() {
        authorsViewModel.authorMottos.observe(viewLifecycleOwner, { _authorMottos ->
            authorMottos = _authorMottos
            displayAuthorMottosRecycler(authorMottos)
        })
    }

    private fun observeDbMottos() {
        mottosViewModel.allMottos.observe(viewLifecycleOwner) { allMottos ->
            allDbMottos = allMottos
        }
    }

    private fun displayAuthorsRecycler(authors: ArrayList<Author>) {
        Arrow.hideBackArrow(activity as MainActivity)

        val layoutManager = GridLayoutManager(context, 2)
        val adapter = AuthorsRecyclerAdapter(this@AuthorsFragment, authors)

        authorsRecycler.setHasFixedSize(true)
        authorsRecycler.layoutManager = layoutManager
        authorsRecycler.adapter = adapter
    }

    private fun displayAuthorMottosRecycler(authorMottos: ArrayList<Motto>) {
        Arrow.displayBackArrow(activity as MainActivity)
        if (authorMottos.isEmpty()) {
            authorsRecycler.visibility = View.GONE
            notFoundMottosTextView.visibility = View.VISIBLE
            mottosLoadingProgressBar.visibility = View.INVISIBLE
        } else {
            notFoundMottosTextView.visibility = View.INVISIBLE
            authorsRecycler.visibility = View.GONE
            mottosLoadingProgressBar.visibility = View.INVISIBLE

            val layoutManager = GridLayoutManager(context, 1)
            val adapter = MottosRecyclerAdapter(authorMottos)

            authorMottosRecycler.scheduleLayoutAnimation()
            authorMottosRecycler.setHasFixedSize(true)
            authorMottosRecycler.layoutManager = layoutManager
            authorMottosRecycler.adapter = adapter
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

    private fun handleAuthorsRecyclerItemClick() {
        authorsRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), authorsRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedAuthor = authors[position]
                    authorsViewModel.putAuthorMottosPostValue(clickedAuthor)
                    mottosLoadingProgressBar.visibility = View.VISIBLE
                }

                override fun onItemLongClick(view: View, position: Int) {
                    clickedAuthor = authors[position]
                    authorsViewModel.putAuthorMottosPostValue(clickedAuthor)
                    mottosLoadingProgressBar.visibility = View.VISIBLE
                }
            })
        )
    }

    private fun handleAuthorMottosRecyclerItemClick() {
        authorMottosRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), authorMottosRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedMotto = authorMottos[position]
                    DialogViewer.displayFullMottoDialog(
                        requireContext(),
                        fullMottoDialog,
                        clickedMotto,
                        allDbMottos
                    )
                    observeDbMottos()
                }

                override fun onItemLongClick(view: View, position: Int) {
                    clickedMotto = authorMottos[position]
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

    private fun initData() {

    }

    private fun initViews() {
        authorsRecycler = binding.recyclerviewAuthorsDashboard
        authorMottosRecycler = binding.recyclerviewAuthorMottos

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
