package github.karchx.motto.ui.tools.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.R
import github.karchx.motto.model.db.Motto
import github.karchx.motto.model.storages.Constants

class FromDbMottosRecyclerAdapter(
    private val mottos: ArrayList<Motto>
) : RecyclerView.Adapter<MottosRecyclerAdapter.MottosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MottosRecyclerAdapter.MottosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.motto_item, parent, false)
        return MottosRecyclerAdapter.MottosViewHolder(view)
    }

    override fun onBindViewHolder(holder: MottosRecyclerAdapter.MottosViewHolder, position: Int) {
        val mottoTitle = getMottoTitle(mottos[position].quote)
        val mottoSource = mottos[position].source
        val mottoDateAdded = mottos[position].dateSaved

        holder.mottoTitle.text = mottoTitle
        holder.mottoSource.text = mottoSource
        holder.mottoDateAdded.visibility = View.VISIBLE
        holder.mottoDateAdded.text = mottoDateAdded
    }

    override fun getItemCount(): Int {
        return mottos.size
    }

    private fun getMottoTitle(quote: String): String {
        val mottoTitleWordsArray = quote.split(" ")

        if (mottoTitleWordsArray.size > Constants.QUANTITY_WORDS_IN_MOTTO_TITLE) {
            val cutMottoTitleWordsArray =
                mottoTitleWordsArray.take(Constants.QUANTITY_WORDS_IN_MOTTO_TITLE)
            var mottoTitle = cutMottoTitleWordsArray.joinToString(separator = " ")
            mottoTitle = "$mottoTitle <...>"
            return mottoTitle
        } else {
            return quote
        }
    }
}
