package github.karchx.motto.views.tools.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.R
import github.karchx.motto.models.db.saved_motto.SavedMotto
import github.karchx.motto.models.storages.Constants

class FromDbMottosRecyclerAdapter(
    private val mottos: List<SavedMotto>
) : RecyclerView.Adapter<MottosRecyclerAdapter.MottosViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MottosRecyclerAdapter.MottosViewHolder {
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
