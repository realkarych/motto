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
import github.karchx.motto.databinding.FragmentAuthorsDashboardBinding
import github.karchx.motto.databinding.FragmentBooksDashboardBinding
import github.karchx.motto.search_engine.citaty_info_website.data.Author
import github.karchx.motto.search_engine.citaty_info_website.data.Book
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.viewmodels.AuthorsDashboardViewModel
import github.karchx.motto.viewmodels.BooksDashboardViewModel
import github.karchx.motto.viewmodels.MottosViewModel
import github.karchx.motto.views.MainActivity
import github.karchx.motto.views.tools.adapters.AuthorsRecyclerAdapter
import github.karchx.motto.views.tools.adapters.BooksRecyclerAdapter
import github.karchx.motto.views.tools.adapters.MottosRecyclerAdapter
import github.karchx.motto.views.tools.listeners.OnClickAddToFavouritesListener
import github.karchx.motto.views.tools.listeners.OnClickRecyclerItemListener
import github.karchx.motto.views.tools.managers.*
import github.karchx.motto.models.db.Motto as dbMotto

class BooksDashboardFragment : Fragment(R.layout.fragment_books_dashboard) {

    private var _binding: FragmentBooksDashboardBinding? = null
    private val binding get() = _binding!!

    // ViewModels
    private lateinit var booksDashboardViewModel: BooksDashboardViewModel
    private lateinit var mottosViewModel: MottosViewModel

    // Views
    private lateinit var booksRecycler: RecyclerView
    private lateinit var bookMottosRecycler: RecyclerView
    private lateinit var mottosLoadingProgressBar: ProgressBar
    private lateinit var fullMottoCardView: CardView
    private lateinit var fullMottoDialog: Dialog
    private lateinit var addToFavouritesImageView: ImageView
    private lateinit var notFoundMottosTextView: TextView

    //Data
    private lateinit var books: ArrayList<Book>
    private lateinit var clickedBook: Book
    private lateinit var allDbMottos: List<dbMotto>
    private lateinit var bookMottos: ArrayList<Motto>
    private lateinit var clickedMotto: Motto

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        booksDashboardViewModel =
            ViewModelProvider(this).get(BooksDashboardViewModel::class.java)
        _binding = FragmentBooksDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        initData()
        initViews()

        observeBooks()
        observeBookMottos()
        observeDbMottos()

        handleBooksRecyclerItemClick()
        handleBookMottosRecyclerItemClick()

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

    private fun observeBooks() {
        booksDashboardViewModel.books.observe(viewLifecycleOwner, { _books ->
            books = _books
            displayBooksRecycler(books)
        })
    }

    private fun observeBookMottos() {
        booksDashboardViewModel.bookMottos.observe(viewLifecycleOwner, { _bookMottos ->
            bookMottos = _bookMottos
            displayBookMottosRecycler(bookMottos)
        })
    }

    private fun observeDbMottos() {
        mottosViewModel.allMottos.observe(viewLifecycleOwner) { allMottos ->
            allDbMottos = allMottos
        }
    }

    private fun displayBooksRecycler(books: ArrayList<Book>) {
        Arrow.hideBackArrow(activity as MainActivity)

        val layoutManager = GridLayoutManager(context, 2)
        val adapter = BooksRecyclerAdapter(this@BooksDashboardFragment, books)

        booksRecycler.setHasFixedSize(true)
        booksRecycler.layoutManager = layoutManager
        booksRecycler.adapter = adapter
    }

    private fun displayBookMottosRecycler(bookMottos: ArrayList<Motto>) {
        Arrow.displayBackArrow(activity as MainActivity)
        if (bookMottos.isEmpty()) {
            booksRecycler.visibility = View.GONE
            notFoundMottosTextView.visibility = View.VISIBLE
            mottosLoadingProgressBar.visibility = View.INVISIBLE
        } else {
            notFoundMottosTextView.visibility = View.INVISIBLE
            booksRecycler.visibility = View.GONE
            mottosLoadingProgressBar.visibility = View.INVISIBLE

            val layoutManager = GridLayoutManager(context, 1)
            val adapter = MottosRecyclerAdapter(bookMottos)

            bookMottosRecycler.scheduleLayoutAnimation()
            bookMottosRecycler.setHasFixedSize(true)
            bookMottosRecycler.layoutManager = layoutManager
            bookMottosRecycler.adapter = adapter
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

    private fun handleBooksRecyclerItemClick() {
        booksRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), booksRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedBook = books[position]
                    booksDashboardViewModel.putBookMottosPostValue(clickedBook)
                    mottosLoadingProgressBar.visibility = View.VISIBLE
                }

                override fun onItemLongClick(view: View, position: Int) {
                    clickedBook = books[position]
                    booksDashboardViewModel.putBookMottosPostValue(clickedBook)
                    mottosLoadingProgressBar.visibility = View.VISIBLE
                }
            })
        )
    }

    private fun handleBookMottosRecyclerItemClick() {
        bookMottosRecycler.addOnItemTouchListener(
            OnClickRecyclerItemListener(requireContext(), bookMottosRecycler, object :
                OnClickRecyclerItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedMotto = bookMottos[position]

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
                    clickedMotto = bookMottos[position]

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
        booksRecycler = binding.recyclerviewBooksDashboard
        bookMottosRecycler = binding.recyclerviewBookMottos

        mottosLoadingProgressBar = binding.progressbarMottosLoading

        fullMottoDialog = Dialog(requireActivity())
        fullMottoDialog.setContentView(R.layout.dialog_full_motto)

        fullMottoCardView = fullMottoDialog.findViewById(R.id.cardview_full_motto_item)
        addToFavouritesImageView = fullMottoDialog.findViewById(R.id.imageview_is_saved_motto)

        notFoundMottosTextView = binding.textviewMottosNotFoundDashboard
    }
}
