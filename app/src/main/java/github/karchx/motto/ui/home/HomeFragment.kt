package github.karchx.motto.ui.home

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import github.karchx.motto.R
import github.karchx.motto.databinding.FragmentHomeBinding
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.ui.home.adapters.RandomMottosRecyclerAdapter
import github.karchx.motto.ui.listeners.OnClickMottoItemListener


class HomeFragment : Fragment() {

    // Data
    private lateinit var mottos: ArrayList<Motto>
    private lateinit var clickedMotto: Motto

    // Views
    private lateinit var mRandomMottosRecycler: RecyclerView
    private lateinit var mSwipeRefreshLayoutRandomMottos: SwipeRefreshLayout
    private lateinit var mFullMottoDialog: Dialog
    private lateinit var mFullMottoCardView: CardView

    // ViewModels
    private lateinit var homeViewModel: HomeViewModel

    //Bindings
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
        initViews()

        homeViewModel.randomMottos.observe(viewLifecycleOwner, Observer {
            mottos = it
            displayMottosRecycler(mottos)

            if (mSwipeRefreshLayoutRandomMottos.isRefreshing) {
                mSwipeRefreshLayoutRandomMottos.isRefreshing = false
            }
        })

        mRandomMottosRecycler.addOnItemTouchListener(
            OnClickMottoItemListener(requireContext(), object :
                OnClickMottoItemListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    clickedMotto = mottos[position]
                    displayFullMottoDialog(clickedMotto)
                }
            })
        )

        mSwipeRefreshLayoutRandomMottos.setOnRefreshListener {
            homeViewModel.putRandomMottosPostValue()
        }

        mFullMottoCardView.setOnClickListener {
            copyText(getMottoDataToCopy())
            displayCopiedToast()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayFullMottoDialog(clickedMotto: Motto) {
        mFullMottoDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mFullMottoDialog.setCancelable(true)
        mFullMottoDialog.setTitle(getString(R.string.motto_about))
        mFullMottoDialog.show()

        val tvFullMottoQuote =
            mFullMottoDialog.findViewById<TextView>(R.id.textview_motto_full_quote)
        val tvFullMottoSource =
            mFullMottoDialog.findViewById<TextView>(R.id.textview_motto_full_source)
        tvFullMottoQuote.text = clickedMotto.quote
        tvFullMottoSource.text = clickedMotto.source
    }

    private fun displayMottosRecycler(mottos: ArrayList<Motto>) {
        val layoutManager = GridLayoutManager(context, 1)
        val adapter = RandomMottosRecyclerAdapter(mottos)

        mRandomMottosRecycler.setHasFixedSize(true)
        mRandomMottosRecycler.layoutManager = layoutManager
        mRandomMottosRecycler.adapter = adapter
    }

    private fun displayCopiedToast() {
        val text = getString(R.string.motto_copied)
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(requireContext().applicationContext, text, duration)
        toast.show()
    }

    private fun getMottoDataToCopy(): String {
        return "\"${clickedMotto.quote}\"\n\n" + "${getString(R.string.motto_source)} ${clickedMotto.source}"
    }

    private fun copyText(text: String) {
        val clipboard: ClipboardManager? =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("stub", text)
        clipboard?.setPrimaryClip(clip)
    }

    private fun initViews() {
        mRandomMottosRecycler = binding.recyclerviewRandomMottos
        mSwipeRefreshLayoutRandomMottos = binding.refreshContainerOfRecyclerviewRandomMottos

        mFullMottoDialog = Dialog(requireActivity())
        mFullMottoDialog.setContentView(R.layout.dialog_full_motto)

        mFullMottoCardView = mFullMottoDialog.findViewById(R.id.cardview_full_motto_item)
    }
}
