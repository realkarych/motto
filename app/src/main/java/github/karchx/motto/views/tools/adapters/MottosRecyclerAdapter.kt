package github.karchx.motto.views.tools.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.R
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.search_engine.citaty_info_website.UIMotto

class MottosRecyclerAdapter(
    private val mottos: ArrayList<UIMotto>
) : RecyclerView.Adapter<MottosRecyclerAdapter.MottosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MottosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.motto_item, parent, false)
        return MottosViewHolder(view)
    }

    override fun onBindViewHolder(holder: MottosViewHolder, position: Int) {
        val mottoTitle = getMottoTitle(mottos[position].quote)
        val mottoSource = mottos[position].source

        holder.mottoTitle.text = mottoTitle
        holder.mottoSource.text = mottoSource
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

    class MottosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mottoTitle: TextView = itemView.findViewById(R.id.textview_motto_title)
        val mottoSource: TextView = itemView.findViewById(R.id.textview_motto_source)
        val mottoDateAdded: TextView = itemView.findViewById(R.id.textview_db_motto_time_added)
    }
}
